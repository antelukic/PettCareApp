package com.pettcare.app.home.network

interface CarePostProfileApi {

    suspend fun response(): List<ApiCarePostProfile>
}
