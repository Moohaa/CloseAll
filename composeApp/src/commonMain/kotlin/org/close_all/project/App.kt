package org.close_all.project

import ControlHeader
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        val apps by remember { mutableStateOf(appManager.getRunningApps()) }
        Column(
            Modifier.fillMaxWidth().padding(5.dp),
        ) {

            ControlHeader(
                onCheckboxChange = { isChecked ->
                    // Handle checkbox state change
                    println("Checkbox is checked: $isChecked")
                },
                shutDownClicked = {
                },
            )
            Divider()

            LazyColumn {
                items(apps) { app ->
                    appRowItem(
                        app = app,
                        onCheckboxChange = { isChecked ->
                            // Handle checkbox state change
                            println("Checkbox is checked: $isChecked")
                        },
                        shutDownClicked = {
                            // Handle button click
                            println("shut down ${app.name}")
                            appManager.closeApp(app)
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