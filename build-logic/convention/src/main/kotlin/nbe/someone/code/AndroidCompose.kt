/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nbe.someone.code

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.io.File

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    pluginManager.apply(libs.findPlugin("compose").get().get().pluginId)

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        kotlinOptions {
            compilerOptions {
                freeCompilerArgs.addAll(buildComposeMetricsParameters())
            }
        }

        dependencies {
            add("implementation", libs.findLibrary("compose-foundation").get())
            add("implementation", libs.findLibrary("compose-material").get())
            add("implementation", libs.findLibrary("compose-preview").get())
            add("debugImplementation", libs.findLibrary("compose-tooling").get())
            add("debugImplementation", libs.findLibrary("compose-test-manifest").get())
        }
    }

    task("copyAndDealComposeMetrics") {
        doLast {

            val srcDir = project.layout.buildDirectory.dir("compose_metrics").get().asFile

            if (srcDir.exists()) {

                val resultDir =
                    rootProject.layout.buildDirectory.dir("compose_metrics/${project.name}")
                        .get().asFile
                resultDir.mkdirs()

                val reportFile = File(resultDir, "result.txt")
                reportFile.delete()


                val resultList = mutableListOf<String>()

                srcDir.listFiles()?.forEach { srcFile ->
                    srcFile.copyTo(File(resultDir, srcFile.name))
                }

                srcDir.listFiles()?.filter { it.extension == "csv" }
                    ?.forEach { srcFile ->
                        srcFile.forEachLine { line ->
                            val columns = line.split(",")
                            val skippable = columns[3] == "1"
                            val restartable = columns[4] == "1"

                            if (skippable.not() && restartable) {
                                resultList.add("${columns[0]}.${columns[1]}")
                            }
                        }
                    }

                if (resultList.isNotEmpty()) {
                    reportFile.createNewFile()

                    resultList.distinct()

                    reportFile.writeText(
                        resultList.joinToString(
                            prefix = "${resultList.size}\n",
                            separator = "\n"
                        )
                    )
                }
            }
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {

    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")

    if (enableMetricsProvider.orNull == "true") {

        val metricsFolder = project.layout.buildDirectory.dir("compose_metrics").get().asFile
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
        )

        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + metricsFolder.absolutePath
        )
    }

    return metricParameters.toList()
}
