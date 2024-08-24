package com.pettcare.app.profile.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class UserServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : UserService {

    override suspend fun getUserById(id: String): BaseApiResponse<UserResponseApi>? = kotlin.runCatching {
        client.get {
            url(USER)
            parameter(ID_PARAMETER, id)
            sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                header("Authorization", "Bearer $token")
            }
        }.body() as BaseApiResponse<UserResponseApi>
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    companion object {
        private const val USER = "user"
        private const val ID_PARAMETER = "id"
    }
}
