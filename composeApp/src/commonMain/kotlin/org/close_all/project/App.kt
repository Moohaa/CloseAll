package org.close_all.project

import ControlHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.close_all.project.data.App
import org.close_all.project.data.Message
import org.close_all.project.data.MessageType
import org.close_all.project.data.Result
import org.close_all.project.service.AppManager
import org.close_all.project.ui.appRowItem
import org.close_all.project.ui.shared.Divider
import org.close_all.project.ui.shared.MessageBox
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    appManager: AppManager
) {
    val appState = AppState(appManager)


    AppTheme {
        val apps = appState.apps.collectAsState()
        val loading = appState.loading.collectAsState()
        val allCheck = appState.allCheck.collectAsState()
        val message = appState.message.collectAsState()

        Box(
            Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(5.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                Modifier.fillMaxWidth().background(MaterialTheme.colors.background)
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
}

class AppState(private val appManager: AppManager) : ViewModel() {
    private val _apps = MutableStateFlow(emptyList<App>())
    val apps = _apps.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _allCheck: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    val allCheck = _allCheck.asStateFlow()

    private var _message: MutableStateFlow<Message?> = MutableStateFlow(null)
    val message = _message.asStateFlow()

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
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                appManager.closeApps(appToClose)
            }
            when (result) {
                is Result.Success -> {
                    reload()
                }

                is Result.Failure -> {
                    _loading.value = false
                    setMessage(Message(result.exception.message!!, MessageType.ERROR)) {
                        reload()
                    }
                    println("${result.exception.message}")
                }
            }
        }
    }


    fun reload() {
        getRunningApps()
    }

    private fun getRunningApps() {
        _loading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                appManager.getRunningApps()
            }
            when (result) {
                is Result.Success -> {
                    _loading.value = false
                    _allCheck.value = false
                    _apps.value = result.data
                }

                is Result.Failure -> {
                    _loading.value = false
                    setMessage(Message(result.exception.message!!, MessageType.ERROR)) {
                        reload()
                    }
                }
            }
        }

    }

    private fun setMessage(message: Message, callBack: () -> Unit) {
        viewModelScope.launch {
            _message.value = message
            println(_message.value!!.message + " " + _message.value!!.type)
            withContext(Dispatchers.Default) {
                delay(3000)
            }
            println(_message.value!!.message + " :" + _message.value!!.type)
            _message.value = null
            callBack()
        }

    }

}

