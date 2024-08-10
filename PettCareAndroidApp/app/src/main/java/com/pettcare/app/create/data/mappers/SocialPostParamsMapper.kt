package com.pettcare.app.create.data.mappers

import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.network.model.CreateSocialPostRequestApi

fun SocialPostParams.toApi(photoUrl: String?, photoId: String) = CreateSocialPostRequestApi(
    text = text,
    photoUrl = photoUrl,
    photoId = photoId,
)
