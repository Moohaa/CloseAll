package org.close_all.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import org.close_all.project.service.AppManager
import org.close_all.project.state.AppState
import org.close_all.project.state.AppsViewState
import org.close_all.project.ui.AppSettings
import org.close_all.project.ui.AppsView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    appManager: AppManager,
    shutDown: () -> Unit
) {

    val isDarkMode = AppState.INSTANCE.is_darkMode.collectAsState()

    AppTheme(
        darkTheme = isDarkMode.value
    ) {
        Box(
            modifier =
            Modifier.fillMaxSize().background(MaterialTheme.colors.surface).padding(5.dp),
        ) {
            var openSettings by remember { mutableStateOf(false) }

            if (!openSettings) {
                val appState = AppsViewState(appManager)

                AppsView(
                    appState,
                    settingsClicked = {
                        openSettings = true
                    }
                )
            } else {
                AppSettings(
                    shutDown = shutDown,
                    onClose = {
                        openSettings = false
                    }
                )
            }
        }
    }
}



