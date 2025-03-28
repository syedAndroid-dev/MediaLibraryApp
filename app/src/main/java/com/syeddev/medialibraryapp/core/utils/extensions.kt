package com.syeddev.medialibraryapp.core.utils

import android.net.Uri


private fun Uri.getExtension(): String {
    val path = this.toString()
    return path.substringAfterLast('.', "").lowercase()
}