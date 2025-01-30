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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.close_all.project.data.App
import org.close_all.project.data.Result
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
                loading = loading.value,
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

class AppState(private val appManager: AppManager) : ViewModel() {
    private val _apps = MutableStateFlow(emptyList<App>())
    val apps = _apps.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _allCheck: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    val allCheck = _allCheck.asStateFlow()


    init {
        getRunningApps()
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
        if (checkedCount == 0) this._allCheck.value = false
        if (checkedCount >= 1) this._allCheck.value = null
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
        when (val result = appManager.closeApps(appToClose)) {
            is Result.Success -> {
                reload()
            }

            is Result.Failure -> {
                _loading.value = false
            }
        }

    }


    fun closeApp(app: App) {
        when (val result = appManager.closeApp(app)) {
            is Result.Success -> {
                println("App closed successfully: ${result.data}")
                reload()
            }

            is Result.Failure -> {
                println("Error: ${result.exception.message}")
                _loading.value = false
            }
        }
    }

    fun reload() {
        getRunningApps()
    }

    private fun getRunningApps() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = appManager.getRunningApps()) {
                is Result.Success -> {
                    delay(1000)
                    _loading.value = false
                    _allCheck.value = false
                    _apps.value = result.data
                }

                is Result.Failure -> {
                    _loading.value = false
                }
            }
        }

    }

}

