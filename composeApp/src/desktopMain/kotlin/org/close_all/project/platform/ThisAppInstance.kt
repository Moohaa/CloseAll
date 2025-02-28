package org.close_all.project.platform

import org.close_all.project.AppData
import java.io.BufferedReader
import java.io.InputStreamReader


object ThisAppInstance {

    fun isAnotherInstanceRunning(): Boolean {
        val appName = AppData.getAppName()
        val process = ProcessBuilder("pgrep", "-f", appName).start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        return reader.readLine() != null // If there is output, another instance is running
    }

}