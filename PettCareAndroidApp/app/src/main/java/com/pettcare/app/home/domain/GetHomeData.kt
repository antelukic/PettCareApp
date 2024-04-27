package com.pettcare.app.home.domain

class GetHomeData(
    private val homeRepository: HomeRepository,
) {

    fun results() = homeRepository.result()

    suspend fun publishPage(page: Int) = homeRepository.publishPage(page)
}
