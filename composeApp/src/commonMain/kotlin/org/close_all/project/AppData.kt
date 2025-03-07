package org.close_all.project

import org.close_all.project.utils.getPropertiesLoader


class AppData {

    companion object {
        private val properties = getPropertiesLoader().loadProperties()


        val APPS_PATH_EXP = "/Applications(/[^/]*)?/[^/]+\\.app/Contents"
        val RELAVANT_CORE_SERVICES_EXP = when (getRelevantCoreServices().size) {
            0 -> "^#1ZAZ321D2"
            else -> {
                var res = "/CoreServices(?:/[^/]*)?/(" + getRelevantCoreServices()
                    .joinToString("|") + ")\\.app/Contents"

                if (getRelevantCoreServices().size == 1) {
                    val l = getRelevantCoreServices()[0]
                    if (l == "*") res = "/CoreServices(/[^/]*)?/[^/]+\\.app/Contents"
                    if (l == "None") res = "^#1ZAZ321D2"
                }
                res
            }
        }

        fun getAppName(): String {
            return properties.get("app.name")!!
        }

        fun getRelevantCoreServices(): List<String> {
            return properties.get("app.relevantCoreServices")!!.split(",")
        }
    }
}