package org.close_all.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.close_all.project.service.DesktopAppManager

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CloseAll",
    ) {
        App(appManager = DesktopAppManager())
    }
}