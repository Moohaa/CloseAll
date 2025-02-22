package org.close_all.project.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.close_all.project.db.getDb


class AppState private constructor() : ViewModel() {

    companion object {
        val INSTANCE = AppState()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = db.getAllHiddenApps()
            _hiddenApps.value = data.toMutableSet()
        }
    }

    private val _is_darkMode = MutableStateFlow(true)
    val is_darkMode = _is_darkMode.asStateFlow()


    private var _hiddenApps = MutableStateFlow(mutableSetOf<String>())
    val hiddenApps = _hiddenApps.asStateFlow()

    private val db = getDb().hiddenAppDao()


    fun changeMode() {
        _is_darkMode.value = !_is_darkMode.value
    }

    fun unHideApp(appName: String) {
        val updatedSet = _hiddenApps.value.toMutableSet()
        updatedSet.remove(appName)

        viewModelScope.launch(Dispatchers.IO) {
            getDb().hiddenAppDao().delete(appName)
            _hiddenApps.value = updatedSet
        }
    }

    fun setHiddenApps(apps: Set<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            getDb().hiddenAppDao().insertHiddenApps(apps.toList())
            _hiddenApps.value.addAll(apps)
        }
    }

    fun getHiddenApps(): Set<String> {
        return _hiddenApps.value
    }

}