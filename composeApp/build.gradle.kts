import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileReader
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}


val appProperties = Properties()
appProperties.load(
    FileReader(
        project.projectDir.toPath()
            .resolve("src/commonMain/resources/app.properties")
            .toFile(),
    ),
)

kotlin {
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
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

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
            packageName = appProperties.getProperty("app.name")

            packageVersion = "1.0.1"
            macOS {
                bundleID = "com.closeAll.mac"
                appCategory = "public.app-category.utilities"

                iconFile.set(project.file("src/commonMain/composeResources/drawable/closeAll_icon.icns"))

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


room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}



