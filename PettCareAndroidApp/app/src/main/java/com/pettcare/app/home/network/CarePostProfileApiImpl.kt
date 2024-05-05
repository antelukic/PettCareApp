package com.pettcare.app.home.network

import kotlin.random.Random

private const val COERCE_MOST_VALUE = 1.3
private const val MOCK_ITEMS_IN_LIST_NUMBER = 15

internal class CarePostProfileApiImpl : CarePostProfileApi {

    override suspend fun response(): List<ApiCarePostProfile> = List(MOCK_ITEMS_IN_LIST_NUMBER) {
        ApiCarePostProfile(
            name = "Darwin Ware",
            id = "$it",
            photoUrl = null,
            price = Random.nextDouble().toString(),
            description = "accumsan",
            lat = 8.9f * Random.nextDouble().coerceAtMost(COERCE_MOST_VALUE),
            lon = 10.11f * Random.nextDouble().coerceAtMost(COERCE_MOST_VALUE),
            address = "Long Beach, Washington road 1$it A",
        )
    }
}
