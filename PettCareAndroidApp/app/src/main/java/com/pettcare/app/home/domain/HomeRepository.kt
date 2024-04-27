package com.pettcare.app.home.domain

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.home.domain.model.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun result(): Flow<BaseResponse<HomeData>>

    suspend fun publishPage(value: Int)
}
