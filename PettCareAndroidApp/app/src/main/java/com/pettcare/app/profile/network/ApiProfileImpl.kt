package com.pettcare.app.profile.network

import com.pettcare.app.profile.network.model.ProfileApi

internal class ApiProfileImpl : ApiProfile {

    override suspend fun getProfileById(id: String): ProfileApi = ProfileApi(
        id = "",
        name = "Lorem Ipsum",
        dateOfBirth = "10/11/2000",
        gender = "Male",
        photoUrl = "",
        email = "someemail@gmail.com",
    )
}
