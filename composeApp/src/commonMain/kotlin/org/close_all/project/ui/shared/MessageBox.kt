package org.close_all.project.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.close_all.project.data.Message
import org.close_all.project.data.MessageType


@Composable
fun MessageBox(
    message: Message
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colors.error).padding(8.dp)
    ) {
        Text(
            text = message.message,
            color = if (message.type == MessageType.ERROR) MaterialTheme.colors.onError else Color.Green,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}