package org.close_all.project.service

import org.close_all.project.data.App

interface AppManager {
    fun getRunningApps(): List<App>
    fun closeApp(app: App)
}


