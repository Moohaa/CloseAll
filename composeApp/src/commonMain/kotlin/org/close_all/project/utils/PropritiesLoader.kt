package org.close_all.project.utils


interface PropertiesLoader {
    fun loadProperties(): Map<String, String>
}

expect fun getPropertiesLoader(): PropertiesLoader
