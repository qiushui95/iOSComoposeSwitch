import com.android.build.api.dsl.ApplicationExtension
import nbe.someone.code.configSpotless
import nbe.someone.code.configureKotlin
import nbe.someone.code.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35

                defaultConfig.ndk {
                    abiFilters.add("armeabi-v7a")
                    abiFilters.add("arm64-v8a")
                }
            }

            configureKotlin()

            configSpotless()
        }
    }
}
