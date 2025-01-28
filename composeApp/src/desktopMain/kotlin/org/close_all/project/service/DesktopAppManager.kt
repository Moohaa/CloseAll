package org.close_all.project.service

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.close_all.project.data.App
import org.close_all.project.data.AppInstance
import java.util.Optional
import java.util.stream.Collectors
import kotlin.time.Duration

class DesktopAppManager : AppManager {

    private val APPS_PATH = "Applications"

    override fun getRunningApps(
        onExit: (apps: List<App>) -> Unit
    ) {
        val appsMap = ProcessHandle.allProcesses().filter { pr ->
            pr.info().command().orElse("Unknown").contains("/${APPS_PATH}/")
        }.filter { pr ->
            pr.parent().get().pid() == 1L
        }.map { pr ->
            val name = getAppName(pr.info().command())
            val startInstance = Instant.fromEpochMilliseconds(
                pr.info().startInstant().orElse(
                    java.time.Instant.now()
                ).epochSecond
            )
            val totalCpuDuration = Duration.parseIsoString(
                pr.info().totalCpuDuration().orElse(
                    java.time.Duration.ZERO
                ).toString()
            )
            AppInstance(
                name,
                pr.pid(),
                pr.info().command().orElse("NA"),
                startInstance,
                totalCpuDuration
            )
        }.collect(Collectors.toList())
            .groupBy { it.name }
            .map { appInstance ->
                val appName = appInstance.key
                val appStart = Clock.System.now()
                val appUser = "To be done"
                val processInstances = appInstance.value
                App(
                    name = appName,
                    startTime = appStart,
                    user = appUser,
                    processInstances = processInstances,
                    totalCpuDurations = Duration.ZERO
                )
            }
        onExit(appsMap)
    }

    override fun closeApp(app: App, onExit: () -> Unit) {
        app.processInstances.forEach { ins ->
            val insProcessHandleOpt = ProcessHandle.of(ins.pid)
            if (insProcessHandleOpt.isPresent) {
                val insProcessHandle = insProcessHandleOpt.get()
                val destroyed = insProcessHandle.destroy()
                println(destroyed)
            }
        }
        onExit()
    }

    override fun closeApps(apps: List<App>, onExit: () -> Unit) {
        apps.forEach { app ->
            closeApp(app) {
            }
        }
        onExit()
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