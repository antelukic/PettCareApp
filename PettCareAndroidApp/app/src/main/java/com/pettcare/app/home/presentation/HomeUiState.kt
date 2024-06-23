package com.pettcare.app.home.presentation

import com.google.android.gms.maps.model.LatLng
import com.pettcare.app.home.presentation.cluster.MapsMarkerCluster
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val profiles: ImmutableList<PresentableProfiles> = persistentListOf(),
    val markers: ImmutableList<MapsMarkerCluster> = persistentListOf(),
)

data class PresentableProfiles(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val description: String,
    val price: String?,
    val location: LatLng,
    val address: String?,
)
