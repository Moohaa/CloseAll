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
    val appState = AppState(appManager)

    MaterialTheme {
        val apps = appState.apps.collectAsState()
        val loading = appState.loading.collectAsState()
        val allCheck = appState.allCheck.collectAsState()

        Column(
            Modifier.fillMaxWidth().padding(5.dp),
        ) {
            ControlHeader(
                checked = allCheck.value,
                onCheckboxChange = {
                    appState.appAllCheckBoxChange()
                },
                shutDownClicked = {
                    appState.closeCheckedApps()
                },
                loadClicked = {
                    appState.reload()
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
                            appState.closeApp(app)
                        },
                        onOptionsClick = {
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

class AppState(private val appManager: AppManager) {
    private val _apps = MutableStateFlow(emptyList<App>())
    val apps = _apps.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _allCheck: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    val allCheck = _allCheck.asStateFlow()


    init {
        _loading.value = true
        appManager.getRunningApps {
            _apps.value = it
            _loading.value = false
        }
    }

    fun appCheckBoxChange(name: String) {
        var checkedCount = 0

        this._apps.value =
            this.apps.value.map {
                var targetApp = it
                if (it.name == name) {
                    targetApp = it.copy(checked = !it.checked)
                }
                if (targetApp.checked) checkedCount++
                targetApp
            }
        if (checkedCount < 2) this._allCheck.value = false
        if (checkedCount >= 2) this._allCheck.value = null
    }

    fun appAllCheckBoxChange() {
        this._allCheck.value = if (this._allCheck.value != null) !this._allCheck.value!! else false
        this._apps.value =
            this.apps.value.map {
                it.copy(checked = this._allCheck.value!!)
            }
    }

    fun closeCheckedApps() {
        _loading.value = true
        val appToClose = this.apps.value.filter {
            it.checked
        }
        appManager.closeApps(appToClose) {
            reload()
            this._allCheck.value = false
            _loading.value = false
        }
    }


    fun closeApp(app: App) {
        appManager.closeApp(app) {
            reload()
        }
    }

    fun reload() {
        _loading.value = true
        appManager.getRunningApps {
            _loading.value = false
            _apps.value = it
        }
    }


}