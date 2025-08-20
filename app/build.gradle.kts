@file:Suppress("DEPRECATION")

import org.gradle.kotlin.dsl.androidTestImplementation
import org.gradle.kotlin.dsl.implementation

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	id("org.jetbrains.kotlin.kapt")
}

android {
	namespace = "com.mt.roomsample"
	compileSdk = 36

	defaultConfig {
		applicationId = "com.mt.roomsample"
		minSdk = 24
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}

	buildFeatures {
		viewBinding = true
		dataBinding = true
	}

	// 将 Room 的 schema 导出目录加入 androidTest 的 assets，便于测试与迁移验证
	sourceSets["androidTest"].assets.srcDirs("$projectDir/schemas")
}

// KAPT 配置：开启 Room schema 导出与增量编译
kapt {
	arguments {
		arg("room.schemaLocation", "$projectDir/schemas")
		arg("room.incremental", "true")
		arg("room.expandProjection", "true")
	}
	correctErrorTypes = true
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

	// Kotlin 协程
	implementation(libs.kotlin.coroutines.core)
	implementation(libs.kotlin.coroutines.android)

	// Lifecycle（稳定版 2.8.x）
	implementation(libs.androidx.lifecycle.viewmodel.ktx)
	implementation(libs.androidx.lifecycle.livedata.ktx)

	// Room（使用 KAPT）
	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.ktx)
	kapt(libs.androidx.room.compiler)
	androidTestImplementation(libs.androidx.room.testing)

	implementation(libs.statelayout)
}