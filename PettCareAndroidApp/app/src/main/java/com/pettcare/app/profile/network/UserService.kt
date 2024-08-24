package com.pettcare.app.profile.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.home.network.response.UsersResponseApi

interface UserService {

    suspend fun getUserById(id: String): BaseApiResponse<UserResponseApi>?

    suspend fun searchUsers(query: String): BaseApiResponse<UsersResponseApi>?
}
