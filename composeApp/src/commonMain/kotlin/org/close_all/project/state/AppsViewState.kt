package org.close_all.project.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun hideCheckedApps() {
        _apps.value = this.apps.value.map {
            if (it.checked) it.hidden = true
            it
        }

        AppState.setHiddenApps(_apps.value.filter {
            it.hidden
        }.map { it.name }.toSet())

        reload()
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
                    _apps.value = result.data.map {
                        if (AppState.getHiddenApps().contains(it.name)) {
                            it.hidden = true
                        }
                        it
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