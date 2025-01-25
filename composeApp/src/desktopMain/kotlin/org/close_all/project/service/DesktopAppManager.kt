package org.close_all.project.service

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.close_all.project.data.App
import org.close_all.project.data.AppInstance
import java.util.stream.Collectors
import kotlin.time.Duration

class DesktopAppManager : AppManager {
    override fun getRunningApps(): List<App> {

        val appsMap = ProcessHandle.allProcesses().filter { pr ->
            pr.info().command().orElse("Unknown").startsWith("/Applications/")
        }.filter { pr ->
            pr.parent().get().pid() == 1L
        }.map { pr ->
            val name = pr.info().command().orElse("/test/").split('/')[2]
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
        return appsMap
    }

}