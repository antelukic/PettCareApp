package com.pettcare.app.create.domain.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.create.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun publishLocationQuery(query: String)

    fun results(): Flow<BaseResponse<List<Location>>>
}
