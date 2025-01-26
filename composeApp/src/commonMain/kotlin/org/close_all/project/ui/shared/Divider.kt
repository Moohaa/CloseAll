package org.close_all.project.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun Divider() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).height(0.5.dp)
            .background(Color.Black)
    ) {}
}
