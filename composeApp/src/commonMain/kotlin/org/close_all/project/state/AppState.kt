package org.close_all.project.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppState {

    companion object {
        private val _is_darkMode = MutableStateFlow(true)
        val is_darkMode = _is_darkMode.asStateFlow()

        private var _hiddenApps = MutableStateFlow(mutableSetOf<String>())
        val hiddenApps = _hiddenApps.asStateFlow()


        fun changeMode() {
            _is_darkMode.value = !_is_darkMode.value
        }

        fun unHideApp(appName: String) {
            val updatedSet = _hiddenApps.value.toMutableSet()
            updatedSet.remove(appName)
            _hiddenApps.value = updatedSet
        }

        fun setHiddenApps(apps: Set<String>) {
            _hiddenApps.value.addAll(apps)
        }

        fun getHiddenApps(): Set<String> {
            return _hiddenApps.value
        }
    }
}