package org.close_all.project.utils

import org.close_all.project.platform.PropertiesLoaderImpl

actual fun getPropertiesLoader(): PropertiesLoader {
    return PropertiesLoaderImpl
}