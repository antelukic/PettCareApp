package com.pettcare.app.home.network.service

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.home.network.response.CarePostsResponseApi

interface CarePostService {

    suspend fun carePosts(page: String, userId: String? = null): BaseApiResponse<CarePostsResponseApi>?
}
