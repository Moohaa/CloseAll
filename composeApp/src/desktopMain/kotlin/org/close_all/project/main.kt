package org.close_all.project

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import closeall.composeapp.generated.resources.Res
import closeall.composeapp.generated.resources.compose_multiplatform
import org.close_all.project.platform.CloseItTray
import org.close_all.project.service.DesktopAppManager
import org.jetbrains.compose.resources.painterResource
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

fun main() = application {
    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val winWith = 400.dp
    val winHeight = 500.dp

    val trayState = rememberTrayState()
    val trayIcon = painterResource(Res.drawable.compose_multiplatform)

    var flag by remember { mutableStateOf(false) }
    var showTray by remember { mutableStateOf(false) }

    val state: WindowState = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.TopEnd),
        width = winWith, height = winHeight
    )
    val trayIconMouseListener = object : MouseListener {
        override fun mouseClicked(e: MouseEvent?) {

        }

        override fun mousePressed(e: MouseEvent?) {
            if (e != null) {
                if ((screenSize.width - e.x) >= (10 + winWith.value / 2)) {
                    state.position =
                        WindowPosition(Dp(e.x - (winWith.value / 2)), Dp(e.y.toFloat()))
                }
                if ((screenSize.width - e.x) < (10 + winWith.value / 2)) state.position =
                    WindowPosition(
                        Dp((e.x - (winWith.value - screenSize.width + e.x)).toFloat()),
                        Dp(e.y.toFloat())
                    )

            }
            flag = !flag
        }

        override fun mouseReleased(e: MouseEvent?) {
            println("clicked")
        }

        override fun mouseEntered(e: MouseEvent?) {
            println("clicked")
        }

        override fun mouseExited(e: MouseEvent?) {
            println("clicked")
        }
    }

    CloseItTray(
        icon = trayIcon,
        state = trayState,
        onAction = {

        },
        mouseListener = trayIconMouseListener,
    )

    Window(
        icon = painterResource(Res.drawable.compose_multiplatform),
        state = state,
        onCloseRequest = {
            showTray = true
            exitApplication()
        },
        title = "CloseAll",
        resizable = false,
        alwaysOnTop = true,
        transparent = true,
        undecorated = true,
        visible = flag
    ) {
        DisposableEffect(Unit) {
            val listener = object : MouseListener {
                override fun mouseClicked(e: MouseEvent?) {
                }

                override fun mousePressed(e: MouseEvent?) {
                    if (e != null) {
                        println(e.locationOnScreen.toString())
                    }
                }

                override fun mouseReleased(e: MouseEvent?) {
                }

                override fun mouseEntered(e: MouseEvent?) {
                }

                override fun mouseExited(e: MouseEvent?) {
                    flag = false
                }
            }
            window.addMouseListener(listener)

            onDispose {
                window.removeMouseListener(listener)
            }
        }
        App(
            appManager = DesktopAppManager()
        )
    }


}



