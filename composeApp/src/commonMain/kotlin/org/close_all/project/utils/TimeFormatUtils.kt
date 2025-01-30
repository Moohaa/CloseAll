package org.close_all.project.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class TimeFormatUtils {

    companion object {
        fun getTimeAgo(startTime: Instant): String {
            val now = Clock.System.now()
            val duration = now - startTime
            return formatDuration(duration)
        }


        private fun formatDuration(duration: Duration): String {
            return when {
                duration.inWholeMinutes < 1 -> "${duration.inWholeSeconds}s"
                duration.inWholeHours < 1 -> "${duration.inWholeMinutes}m"
                duration.inWholeDays < 1 -> "${duration.inWholeHours}h"
                duration.inWholeDays < 30 -> "${duration.inWholeDays}d"
                duration.inWholeDays < 365 -> "${duration.inWholeDays / 30}mo"
                else -> "${duration.inWholeDays / 365}y"
            }
        }
    }
}