package com.pettcare.app.home.network.service

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.home.network.response.CarePostsResponseApi
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class CarePostServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : CarePostService {

    override suspend fun carePosts(page: String, userId: String?): BaseApiResponse<CarePostsResponseApi>? =
        kotlin.runCatching {
            client.get {
                url(CARE_POSTS)
                parameter(PAGE_SIZE_PARAMETER, PAGE_SIZE)
                parameter(PAGE_NUMBER_PARAMETER, page)
                userId?.let {
                    parameter(USER_ID_PARAMETER, userId)
                }
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }.body() as BaseApiResponse<CarePostsResponseApi>?
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    companion object {
        private const val CARE_POSTS = "/carePosts"
        private const val PAGE_NUMBER_PARAMETER = "pageNumber"
        private const val PAGE_SIZE_PARAMETER = "pageSize"
        private const val PAGE_SIZE = "10"
        private const val USER_ID_PARAMETER = "userId"
    }
}
