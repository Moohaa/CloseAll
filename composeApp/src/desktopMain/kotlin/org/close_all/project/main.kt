package org.close_all.project

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.close_all.project.service.DesktopAppManager

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CloseAll",
        resizable = false,
        state = rememberWindowState(width = 400.dp, height = 500.dp)
    ) {
        App(appManager = DesktopAppManager())
    }
}