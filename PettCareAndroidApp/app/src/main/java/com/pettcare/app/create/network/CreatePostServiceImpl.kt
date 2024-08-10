package com.pettcare.app.create.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.create.network.model.CreateCarePostRequestApi
import com.pettcare.app.create.network.model.CreateCarePostResponseApi
import com.pettcare.app.create.network.model.CreateSocialPostRequestApi
import com.pettcare.app.create.network.model.CreateSocialPostResponseApi
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class CreatePostServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : CreatePostService {

    override suspend fun createCarePost(params: CreateCarePostRequestApi): BaseApiResponse<CreateCarePostResponseApi>? =
        kotlin.runCatching {
            client.post {
                url(ADD_CARE_POST)
                val userId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
                    ?: return@runCatching null
                setBody(params.copy(creatorId = userId))
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }.body() as BaseApiResponse<CreateCarePostResponseApi>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun createSocialPost(
        params: CreateSocialPostRequestApi,
    ): BaseApiResponse<CreateSocialPostResponseApi>? =
        kotlin.runCatching {
            client.post {
                url(ADD_SOCIAL_POST)
                val userId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
                    ?: return@runCatching null
                setBody(params.copy(creatorId = userId))
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }.body() as BaseApiResponse<CreateSocialPostResponseApi>
        }.onFailure { it.printStackTrace() }
            .getOrNull()

    companion object {
        private const val ADD_SOCIAL_POST = "socialPost/add"
        private const val ADD_CARE_POST = "carePost/add"
    }
}
