package com.pettcare.app.home.domain.model

import com.google.android.gms.maps.model.LatLng

data class CarePost(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val description: String,
    val price: String?,
    val location: LatLng,
    val address: String?,
    val postImageUrl: String?,
)
