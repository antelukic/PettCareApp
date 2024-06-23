package com.pettcare.app.create.domain.model

import com.google.android.gms.maps.model.LatLng

data class Location(
    val name: String,
    val address: String,
    val latLng: LatLng,
)
