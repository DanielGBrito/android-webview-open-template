import java.util.Properties
import org.gradle.api.GradleException
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.roborazzi)
}

fun loadTemplateConfig(): Properties {
  val properties = Properties()
  val exampleFile = rootProject.file(".env.example")
  val envFile = rootProject.file(".env")

  if (exampleFile.exists()) {
    exampleFile.inputStream().use(properties::load)
  }
  if (envFile.exists()) {
    envFile.inputStream().use(properties::load)
  }

  return properties
}

fun Properties.value(name: String, fallback: String): String =
  getProperty(name)?.trim()?.takeIf { it.isNotEmpty() } ?: fallback

fun String.asBuildConfigString(): String =
  "\"" + replace("\\", "\\\\").replace("\"", "\\\"") + "\""

val templateConfig = loadTemplateConfig()
val generatedLogoResDir = layout.buildDirectory.dir("generated/res/appLogo")
val generatedLogoDrawableDir = generatedLogoResDir.map { it.dir("drawable") }
val configuredLogo = templateConfig.value("APP_LOGO_PATH", "")
val configuredLogoFile = if (configuredLogo.isNotEmpty()) rootProject.file(configuredLogo) else null
val sourceLogo = when {
  configuredLogoFile != null && configuredLogoFile.exists() && configuredLogoFile.isFile -> configuredLogoFile
  configuredLogo.isNotEmpty() -> throw GradleException(
    "APP_LOGO_PATH points to '$configuredLogo', but this file was not found. " +
      "Check the file name and extension inside the branding folder."
  )
  else -> project.file("src/main/res/drawable/default_app_logo.xml")
}
val appLogoExtension = when (sourceLogo.extension.lowercase()) {
  "jpg", "jpeg" -> "jpg"
  "png" -> "png"
  "xml" -> "xml"
  else -> "xml"
}

val cleanGeneratedAppLogo by tasks.registering(Delete::class) {
  delete(generatedLogoResDir)
}

val generateAppLogo by tasks.registering(Copy::class) {
  dependsOn(cleanGeneratedAppLogo)
  from(sourceLogo)
  into(generatedLogoDrawableDir)
  rename { "app_logo.$appLogoExtension" }
}

android {
  namespace = "com.opensource.webviewtemplate"
  compileSdk { version = release(36) { minorApiLevel = 1 } }

  defaultConfig {
    applicationId = templateConfig.value("APP_ID", "com.opensource.webviewtemplate")
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    resValue("string", "app_name", templateConfig.value("APP_NAME", "Embedded Web App"))
    manifestPlaceholders["usesCleartextTraffic"] = templateConfig.value("ALLOW_CLEARTEXT_TRAFFIC", "false")

    buildConfigField("String", "APP_NAME", templateConfig.value("APP_NAME", "Embedded Web App").asBuildConfigString())
    buildConfigField("String", "WEBVIEW_URL", templateConfig.value("WEBVIEW_URL", "https://example.com").asBuildConfigString())
    buildConfigField("String", "WEBVIEW_ALLOWED_HOSTS", templateConfig.value("WEBVIEW_ALLOWED_HOSTS", "").asBuildConfigString())
    buildConfigField("String", "SPLASH_SUBTITLE", templateConfig.value("SPLASH_SUBTITLE", "Powered by Android WebView").asBuildConfigString())
    buildConfigField("String", "USER_AGENT_SUFFIX", templateConfig.value("USER_AGENT_SUFFIX", "EmbeddedWebViewApp/1.0").asBuildConfigString())
  }

  buildTypes {
    release {
      isCrunchPngs = false
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
    buildConfig = true
    resValues = true
  }
  sourceSets {
    getByName("main") {
      res.srcDir("build/generated/res/appLogo")
    }
  }
  testOptions { unitTests { isIncludeAndroidResources = true } }
}

tasks.named("preBuild") {
  dependsOn(generateAppLogo)
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  testImplementation(libs.androidx.compose.ui.test.junit4)
  testImplementation(libs.androidx.core)
  testImplementation(libs.androidx.junit)
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.robolectric)
  testImplementation(libs.roborazzi)
  testImplementation(libs.roborazzi.compose)
  testImplementation(libs.roborazzi.junit.rule)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.runner)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  debugImplementation(libs.androidx.compose.ui.tooling)
}
