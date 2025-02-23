package org.close_all.project.ui

import ControlHeader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.close_all.project.state.AppsViewState
import org.close_all.project.ui.shared.Divider
import org.close_all.project.ui.shared.MessageBox


@Composable
fun AppsView(
    appState: AppsViewState,
    settingsClicked: () -> Unit
) {
    val apps = appState.apps.collectAsState()
    val loading = appState.loading.collectAsState()
    val allCheck = appState.allCheck.collectAsState()
    val message = appState.message.collectAsState()

    Box(

        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            ControlHeader(
                checked = allCheck.value,
                loading = loading.value,
                onCheckboxChange = {
                    appState.appAllCheckBoxChange()
                },
                shutDownClicked = {
                    appState.closeCheckedApps()
                },
                loadClicked = {
                    appState.reload()
                },
                hideClicked = {
                    appState.hideCheckedApps()
                },
                settingsClicked = {
                    settingsClicked()
                }
            )
            Divider()


            LazyColumn {
                items(apps.value) { app ->
                    appRowItem(
                        app = app,
                        onCheckboxChange = {
                            appState.appCheckBoxChange(app.name)
                        },
                        shutDownClicked = {
                            //appState.closeApp(app)
                        },
                        onOptionsClick = {
                        }
                    )
                    Divider()
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 25.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (message.value != null) MessageBox(message = message.value!!)
        }
    }
}