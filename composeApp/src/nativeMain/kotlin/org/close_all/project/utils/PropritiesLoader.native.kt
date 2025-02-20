package org.close_all.project.utils


class PropertiesLoaderImpl : PropertiesLoader {
    override fun loadProperties(): Map<String, String> {
        TODO("Not yet implemented")
    }
}

actual fun getPropertiesLoader(): PropertiesLoader {
    return PropertiesLoaderImpl()
}