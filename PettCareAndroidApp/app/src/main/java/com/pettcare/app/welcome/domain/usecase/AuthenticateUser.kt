package com.pettcare.app.welcome.domain.usecase

import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository

class AuthenticateUser(private val authenticationRepository: AuthenticationRepository) {

    val results = authenticationRepository.authenticateResults()

    suspend operator fun invoke() = authenticationRepository.authenticateUser()
}
