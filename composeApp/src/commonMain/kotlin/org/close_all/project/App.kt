package org.close_all.project

import ControlHeader
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.close_all.project.data.App
import org.close_all.project.service.AppManager
import org.close_all.project.ui.appRowItem
import org.close_all.project.ui.shared.Divider
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
    appManager: AppManager
) {
    MaterialTheme {

        val appState = AppState(appManager)

        val apps = appState.apps.collectAsState()
        val loading = appState.loading.collectAsState()

        var allCheckBoxClicked by remember { mutableStateOf(false) }


        Column(
            Modifier.fillMaxWidth().padding(5.dp),
        ) {

            ControlHeader(
                onCheckboxChange = { isChecked ->
                    // Handle checkbox state change
                    println("Checkbox is checked: $isChecked")
                    allCheckBoxClicked = isChecked
                },
                shutDownClicked = {
                },
                loadClicked = {
                    println("load clicked")
                    appState.reload()
                }
            )
            Divider()
            LazyColumn {
                items(apps.value) { app ->
                    appRowItem(
                        app = app,
                        onCheckboxChange = { isChecked ->
                            // Handle checkbox state change
                            println("Checkbox is checked: $isChecked")
                        },
                        shutDownClicked = {
                            // Handle button click
                            println("shut down ${app.name}")
                            appState.closeApp(app)
                        },
                        onOptionsClick = {
                            // Handle options icon click
                            println("Options icon clicked")
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

class AppState(val appManager: AppManager) {
    private val _apps = MutableStateFlow(emptyList<App>())
    val apps = _apps.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    init {
        _loading.value = true
        appManager.getRunningApps {
            _apps.value = it
            _loading.value = false
        }
    }

    fun closeApp(app: App) {
        appManager.closeApp(app) {
            println("closed successfully")
            reload()
        }
    }

    fun reload() {
        _loading.value = true
        appManager.getRunningApps {
            _loading.value = false
            _apps.value = it
            it.forEach { el -> println(el.name) }
        }
    }


}