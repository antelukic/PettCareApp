package com.pettcare.app.create.network

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.SearchByTextResponse
import kotlinx.coroutines.tasks.await

class LocationService(private val placesClient: PlacesClient) {

    suspend fun getLocationsFromQuery(query: String): SearchByTextResponse = placesClient.searchByText(
        SearchByTextRequest.newInstance(
            query,
            listOf(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.NAME),
        ),
    ).await()
}
