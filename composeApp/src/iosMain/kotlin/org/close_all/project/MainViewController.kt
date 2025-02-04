package org.close_all.project

import androidx.compose.ui.window.ComposeUIViewController
import org.close_all.project.data.App
import org.close_all.project.data.Result
import org.close_all.project.service.AppManager

fun MainViewController() = ComposeUIViewController { App(Manager()) }

class Manager : AppManager {
    override suspend fun getRunningApps(): Result<List<App>> {
        TODO("Not yet implemented")
    }

    override fun closeApp(app: App): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun closeApps(apps: List<App>): Result<Boolean> {
        TODO("Not yet implemented")
    }

}