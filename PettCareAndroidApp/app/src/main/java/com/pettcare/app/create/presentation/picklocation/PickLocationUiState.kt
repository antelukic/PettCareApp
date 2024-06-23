package com.pettcare.app.create.presentation.picklocation

import com.google.android.gms.maps.model.LatLng
import com.pettcare.app.extensions.EMPTY
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.UUID

data class PickLocationUiState(
    val text: String = EMPTY,
    val locations: ImmutableList<PresentableLocation> = persistentListOf(),
    val isLoading: Boolean = false,
)

data class PresentableLocation(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val latLng: LatLng,
    val address: String,
)
