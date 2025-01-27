package org.close_all.project.service

import org.close_all.project.data.App

interface AppManager {
    fun getRunningApps(onExit: (apps: List<App>) -> Unit)
    fun closeApp(app: App, onExit: () -> Unit)
}


