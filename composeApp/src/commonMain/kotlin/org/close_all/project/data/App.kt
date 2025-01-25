package org.close_all.project.data

import kotlinx.datetime.Instant
import kotlin.time.Duration


data class App(
    val name: String,
    val startTime: Instant,
    val user: String,
    val processInstances: List<AppInstance>,
    val totalCpuDurations: Duration,
)

data class AppInstance(
    val name: String,
    val pid: Long,
    val cmd: String,
    val startTime: Instant,
    val totalCpuDuration: Duration
)
