package org.close_all.project.service

import kotlinx.datetime.Instant
import org.close_all.project.data.App
import org.close_all.project.data.AppInstance
import org.close_all.project.data.Result
import java.util.Optional
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import kotlin.time.Duration


class DesktopAppManager : AppManager {

    private val APPS_PATH = "Applications"

    override suspend fun getRunningApps(): Result<List<App>> {
        try {
            val appsMap =
                ProcessHandle.of(1L).get().descendants()
                    .filter { pr ->
                        pr.info().commandLine().orElse("Unknown").contains("/${APPS_PATH}/")
                    }.map { pr ->
                        val name = getAppName(pr.info().commandLine())
                        val startInstance = Instant.fromEpochMilliseconds(
                            pr.info().startInstant().orElse(
                                java.time.Instant.now()
                            ).toEpochMilli()
                        )

                        val totalCpuDuration = Duration.parseIsoString(
                            pr.info().totalCpuDuration().orElse(
                                java.time.Duration.ofSeconds(1)
                            ).toString()
                        )
                        AppInstance(
                            name,
                            pr.pid(),
                            pr.info().commandLine().orElse("NA"),
                            startInstance,
                            totalCpuDuration,
                            pr.info().user().orElse("Unknown")
                        )

                    }.collect(Collectors.toList())
                    .groupBy { it.name }
                    .map { appInstances ->
                        val appName = appInstances.key
                        val appStart = appInstances.value.minOf { it.startTime }
                        val appUser =
                            appInstances.value.groupBy { it.user }.keys.filter {
                                it != "Unknown"
                            }.joinToString(",")

                        val processInstances = appInstances.value


                        App(
                            name = appName,
                            startTime = appStart,
                            user = appUser,
                            processInstances = processInstances,
                            totalCpuDurations = Duration.ZERO
                        )
                    }
                    .sortedByDescending {
                        it.startTime
                    }
            return Result.Success(appsMap)
        } catch (exception: Exception) {
            println(exception)
            return Result.Failure(Exception("Error while getting running apps", exception))
        }
    }

    override fun closeApp(app: App): Result<Boolean> {
        try {
            val appInstancesProcesses = app.processInstances.associate { ins ->
                val insProcessHandleOpt = ProcessHandle.of(ins.pid)
                var destroyed = false
                if (insProcessHandleOpt.isPresent) {
                    val insProcessHandle = insProcessHandleOpt.get()
                    destroyed = insProcessHandle.destroy()
                }
                insProcessHandleOpt to destroyed
            }


            // wait for all instances to close
            appInstancesProcesses.forEach {
                it.key.get().onExit().orTimeout(1, TimeUnit.SECONDS).get()
            }

            return Result.Success(true)

        } catch (exception: Exception) {
            return Result.Failure(Exception("Couldn't close ${app.name} app"))
        }

    }

    override suspend fun closeApps(apps: List<App>): Result<Boolean> {
        val appsDidntClose = ArrayList<String>()
        apps.forEach { app ->
            when (val result = closeApp(app)) {
                is Result.Failure -> appsDidntClose.add(app.name)
                is Result.Success -> {
                }
            }
        }
        if (appsDidntClose.isEmpty()) return Result.Success(true)
        return Result.Failure(Exception("⚠\uFE0F Couldn't close ${appsDidntClose.joinToString(",")}"))

    }

    private fun getAppName(cmd: Optional<String>): String {
        // the name of the app listed directly after the /Applications and before .app/Contents/
        // ex /System/Applications/Mail.app/Contents/MacOS/Mail ,/Applications/Zed.app/Contents/MacOS/zed,
        // ,/Applications/UTILITIES/terminal.app/Contents/MacOS/terminal,
        try {
            val listName = cmd.orElse("NA").substringAfter("/Applications/")
                .substringBefore(".app/Contents").split('/')

            return when (listName.size) {
                0 -> "NA"
                1 -> listName.get(0)
                else -> listName.get(1)
            }

        } catch (e: Exception) {
            return ""
        }

    }

}