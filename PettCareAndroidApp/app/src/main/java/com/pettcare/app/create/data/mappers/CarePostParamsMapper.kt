package com.pettcare.app.create.data.mappers

import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.network.model.CreateCarePostRequestApi

fun CarePostParams.toApi(photoUrl: String?, photoId: String) = CreateCarePostRequestApi(
    description = description,
    lat = lat,
    lon = lon,
    address = address,
    price = price,
    photoUrl = photoUrl,
    photoId = photoId,
)
