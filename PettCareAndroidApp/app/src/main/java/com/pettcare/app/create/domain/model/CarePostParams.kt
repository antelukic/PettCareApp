package com.pettcare.app.create.domain.model

import android.net.Uri

class CarePostParams(
    val description: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val price: String?,
    val photo: Uri?,
)
