package org.close_all.project.service

import org.close_all.project.data.App
import org.close_all.project.data.Result

interface AppManager {
    suspend fun getRunningApps(): Result<List<App>>
    fun closeApp(app: App): Result<Boolean>
    fun closeApps(apps: List<App>): Result<Boolean>
}


