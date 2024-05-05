package com.pettcare.app.profile.network

import com.pettcare.app.profile.network.model.ProfileApi

interface ApiProfile {

    suspend fun getProfileById(id: String): ProfileApi
}
