package com.pettcare.app.create.data.repository

import com.google.android.libraries.places.api.model.Place
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.create.domain.model.Location
import com.pettcare.app.create.domain.repository.LocationRepository
import com.pettcare.app.create.network.LocationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

private const val DEBOUNCE_TIME = 300L

internal class LocationRepositoryImpl(private val locationService: LocationService) : LocationRepository {

    private val queryPublisher = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 1)

    override suspend fun publishLocationQuery(query: String) {
        queryPublisher.emit(query)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun results(): Flow<BaseResponse<List<Location>>> = queryPublisher
        .debounce(DEBOUNCE_TIME)
        .mapLatest(::runQuery)
        .onStart { BaseResponse.Loading }

    private fun List<Place>.toLocationApi(): List<Location> {
        return mapNotNull { place ->
            Location(
                name = place.name ?: return@mapNotNull null,
                address = place.address.orEmpty(),
                latLng = place.latLng ?: return@mapNotNull null,
            )
        }
    }

    private suspend fun runQuery(query: String) = runCatching {
        if (query.isBlank()) {
            emptyList()
        } else {
            locationService.getLocationsFromQuery(query).places.toLocationApi()
        }
    }.fold(
        onSuccess = { BaseResponse.Success(it) },
        onFailure = { BaseResponse.Error.Network },
    )
}
