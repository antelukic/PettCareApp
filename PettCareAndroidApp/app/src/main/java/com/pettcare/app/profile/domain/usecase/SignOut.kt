package com.pettcare.app.profile.domain.usecase

import com.pettcare.app.profile.domain.repository.ProfileRepository

class SignOut(private val repository: ProfileRepository) {

    operator fun invoke() = repository.signOut()
}
