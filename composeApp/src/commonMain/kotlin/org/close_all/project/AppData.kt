package org.close_all.project

import org.close_all.project.utils.getPropertiesLoader


class AppData {

    companion object {
        private val appName: String = ""

        private val properties = getPropertiesLoader().loadProperties()

        fun getAppName(): String {
            return properties.get("app.name")!!
        }


    }
}