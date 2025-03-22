package com.syeddev.medialibraryapp.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    inline fun <reified T : Any> objectType(isNullableAllowed: Boolean = true): NavType<T> {
        return object : NavType<T>(isNullableAllowed) {
            override fun put(bundle: Bundle, key: String, value: T) {
                bundle.putString(key, Json.encodeToString(value))
            }

            override fun get(bundle: Bundle, key: String): T? {
                return Json.decodeFromString(bundle.getString(key) ?: return null)
            }

            override fun serializeAsValue(value: T): String {
                return Uri.encode(Json.encodeToString(value))
            }

            override fun parseValue(value: String): T {
                return Json.decodeFromString(Uri.decode(value))
            }
        }
    }
}