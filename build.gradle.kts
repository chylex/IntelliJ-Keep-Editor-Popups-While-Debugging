@file:Suppress("ConvertLambdaToReference")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.10"
	id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.chylex.intellij.keeppopupswhiledebugging"
version = "0.0.1"

repositories {
	mavenCentral()
}

intellij {
	version.set("2022.1")
	updateSinceUntilBuild.set(false)
}

tasks.patchPluginXml {
	sinceBuild.set("202")
}

tasks.buildSearchableOptions {
	enabled = false
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
	kotlinOptions.freeCompilerArgs = listOf(
		"-Xjvm-default=enable"
	)
}
