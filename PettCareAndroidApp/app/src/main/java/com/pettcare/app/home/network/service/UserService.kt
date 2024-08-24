package com.pettcare.app.home.network.service

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.home.network.response.UserResponseApi

interface UserService {

    suspend fun getUserById(id: String): BaseApiResponse<UserResponseApi>?
}
