// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kspPlugin) apply false
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.googleServices) version libs.versions.android.gsm apply false
    alias(libs.plugins.room) version libs.versions.room.db apply false
}