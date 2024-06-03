package com.pettcare.app.create.data.mappers

import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.network.model.SocialPostParamsApi

fun SocialPostParams.toApi(photoUrl: String?, photoId: String) = SocialPostParamsApi(
    text = text,
    photoUrl = photoUrl,
    photoId = photoId,
)
