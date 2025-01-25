package org.close_all.project

import ControlHeader
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.close_all.project.service.AppManager
import org.close_all.project.ui.appRowItem
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                }) {
                Text("Close all")
            }
            ControlHeader(
                onCheckboxChange = { isChecked ->
                    // Handle checkbox state change
                    println("Checkbox is checked: $isChecked")
                },
                onSearch = { query ->
                    // Handle search action
                    println("Search query: $query")
                }
            )
            LazyColumn {
                items(apps) { app ->
                    appRowItem(
                        name = app.name,
                        onCheckboxChange = { isChecked ->
                            // Handle checkbox state change
                            println("Checkbox is checked: $isChecked")
                        },
                        onButtonClick = {
                            // Handle button click
                            println("Button clicked")
                        },
                        onOptionsClick = {
                            // Handle options icon click
                            println("Options icon clicked")
                        }
                    )
                }
            }
        }
    }
}