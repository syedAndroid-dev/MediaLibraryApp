package com.syeddev.medialibraryapp.core.apiutils

sealed class Resource<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Success<T>(data: T? = null, message: String? = "") : Resource<T>(data = data)

    class Error<T>(data: T? = null, message: String? = "") : Resource<T>(message = message)
}