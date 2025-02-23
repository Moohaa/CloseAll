package org.close_all.project.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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


class AppsViewState(private val appManager: AppManager) : ViewModel() {


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

        this._allCheck.value = when (checkedCount) {
            0 -> false
            this._apps.value.size -> true
            else -> null
        }
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
                }
            }
        }
    }

    fun hideCheckedApps() {
        val appToHide = _apps.value.filter { it.checked }.map { it.name }.toSet()

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                AppState.INSTANCE.setHiddenApps(appToHide)
            }
            reload()
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
                    _apps.value = result.data.filter {
                        !AppState.INSTANCE.getHiddenApps().contains(it.name)
                    }
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
            withContext(Dispatchers.Default) {
                delay(3000)
            }
            _message.value = null
            callBack()
        }
    }
}