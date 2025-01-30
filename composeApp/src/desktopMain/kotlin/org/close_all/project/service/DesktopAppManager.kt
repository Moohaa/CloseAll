package org.close_all.project.service

import kotlinx.datetime.Instant
import org.close_all.project.data.App
import org.close_all.project.data.AppInstance
import org.close_all.project.data.Result
import java.util.Optional
import java.util.stream.Collectors
import kotlin.time.Duration

class DesktopAppManager : AppManager {

    private val APPS_PATH = "Applications"

    override suspend fun getRunningApps(): Result<List<App>> {
        try {
            val appsMap = ProcessHandle.allProcesses().filter { pr ->
                pr.info().command().orElse("Unknown").contains("/${APPS_PATH}/")
            }.filter { pr ->
                pr.parent().get().pid() == 1L
            }.map { pr ->
                val name = getAppName(pr.info().command())
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
                    pr.info().command().orElse("NA"),
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
            return Result.Success(appsMap)
        } catch (exception: Exception) {
            println(exception)
            return Result.Failure(Exception("Error while getting running apps", exception))
        }
    }

    override fun closeApp(app: App): Result<Boolean> {
        var allInstanceClosed = true
        app.processInstances.forEach { ins ->
            val insProcessHandleOpt = ProcessHandle.of(ins.pid)
            if (insProcessHandleOpt.isPresent) {
                val insProcessHandle = insProcessHandleOpt.get()
                val destroyed = insProcessHandle.destroy()
                allInstanceClosed = destroyed && allInstanceClosed
            }
        }
        if (allInstanceClosed) return Result.Success(true)
        return Result.Failure(Exception("Couldn't close ${app.name} app"))
    }

    override fun closeApps(apps: List<App>): Result<Boolean> {
        val appsDidntClose = ArrayList<String>()
        apps.forEach { app ->
            when (val result = closeApp(app)) {
                is Result.Failure -> appsDidntClose.add(app.name)
                is Result.Success -> {}
            }
        }
        if (appsDidntClose.isEmpty()) return Result.Success(true)
        return Result.Failure(Exception("Couldn't close ${appsDidntClose.joinToString(",")} apps"))

    }

    private fun getAppName(cmd: Optional<String>): String {
        // the name of the app came directly after the /Applications
        // ex /System/Applications/Mail.app/Contents/MacOS/Mail ,/Applications/Zed.app/Contents/MacOS/zed,
        try {
            val pathItems = cmd.orElse("/non/").split('/')

            val appsInd = pathItems.indexOf(APPS_PATH)
            return pathItems.get(index = appsInd + 1).split('.').get(0)

        } catch (e: Exception) {
            return ""
        }

    }

}