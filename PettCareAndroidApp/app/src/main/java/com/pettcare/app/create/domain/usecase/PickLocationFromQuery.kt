package com.pettcare.app.create.domain.usecase

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.create.domain.model.Location
import com.pettcare.app.create.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class PickLocationFromQuery(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(query: String) = locationRepository.publishLocationQuery(query)

    fun results(): Flow<BaseResponse<List<Location>>> = locationRepository.results()
}
