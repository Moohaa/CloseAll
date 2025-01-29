package org.close_all.project.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.close_all.project.data.App
import kotlin.time.Duration

@Composable
fun appRowItem(
    app: App,
    onCheckboxChange: () -> Unit,
    shutDownClicked: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.height(40.dp)
    ) {
        Checkbox(
            checked = app.checked,
            modifier = Modifier.size(20.dp),
            onCheckedChange = {
                onCheckboxChange()
            }
        )

        Text(
            text = app.name,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
        )

        Text(
            text = getTimeAgo(app.startTime),
            modifier = Modifier
        )

//        IconButton(
//            onClick = shutDownClicked,
//        ) {
//            Icon(
//                imageVector = Cancel,
//                contentDescription = "Shutdown",
//                tint = Color.Red,
//            )
//        }
    }
}

fun getTimeAgo(startTime: Instant): String {
    val now = Clock.System.now()
    val duration = now - startTime
    return formatDuration(duration)
}


fun formatDuration(duration: Duration): String {
    return when {
        duration.inWholeMinutes < 1 -> "${duration.inWholeSeconds}s"
        duration.inWholeHours < 1 -> "${duration.inWholeMinutes}m"
        duration.inWholeDays < 1 -> "${duration.inWholeHours}h"
        duration.inWholeDays < 30 -> "${duration.inWholeDays}d"
        duration.inWholeDays < 365 -> "${duration.inWholeDays / 30}mo"
        else -> "${duration.inWholeDays / 365}y"
    }
}