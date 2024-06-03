package com.pettcare.app.create.network

import com.pettcare.app.create.network.model.CarePostParamsApi
import com.pettcare.app.create.network.model.SocialPostParamsApi

class ApiCreatePostImpl : ApiCreatePost {

    override suspend fun createCarePost(params: CarePostParamsApi) {
        // no op until backend is ready
    }

    override suspend fun createSocialPost(params: SocialPostParamsApi) {
        // no op until backend is ready
    }
}
