package com.syeddev.medialibraryapp.core.apiutils

sealed class Resource(
   // val data : T? = null,
    val message : String? = null
) {
    class Success() : Resource()

    class Error(message: String?) : Resource(message)
}