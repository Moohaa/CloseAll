import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.close_all.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "closeAll"
            packageVersion = "1.0.0"
            macOS {
                bundleID = "com.closeAll.mac"
                appCategory = "public.app-category.utilities"
                infoPlist {
                    dockName = "CloseAll"
                    extraKeysRawXml = """
                        <key>LSUIElement</key>
                        <string>true</string>
                    """
                }
                jvmArgs("-Xms20m")
                jvmArgs("-Xmx30m")
                jvmArgs("-Dapple.awt.enableTemplateImages=true")
                jvmArgs("-Dmac.bundleID=$bundleID")
            }
        }
    }
}


