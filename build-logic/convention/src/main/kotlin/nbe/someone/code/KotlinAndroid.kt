package nbe.someone.code

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    javaVersion: JavaVersion = JavaVersion.VERSION_17,
) {

    commonExtension.apply {
        compileSdk = 35


        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }
}

internal fun Project.configureKotlin(
    jvmTarget: JvmTarget = JvmTarget.JVM_17
) {
    kotlinOptions {
        compilerOptions {
            this.jvmTarget.set(jvmTarget)
        }
    }
}

internal fun Project.kotlinOptions(block: KotlinAndroidProjectExtension.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlin", block)
}
