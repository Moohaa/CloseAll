package org.close_all.project.platform

import org.close_all.project.utils.PropertiesLoader
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.Properties


class PropertiesLoaderImpl : PropertiesLoader {
    override fun loadProperties(): Map<String, String> {
        val properties = Properties()
        val inputStream: InputStream = this::class.java.getResourceAsStream("/app.properties")
            ?: throw FileNotFoundException("Properties file not found")
        inputStream.use { properties.load(it) }
        return properties.stringPropertyNames().associateWith { properties.getProperty(it) }
    }
}