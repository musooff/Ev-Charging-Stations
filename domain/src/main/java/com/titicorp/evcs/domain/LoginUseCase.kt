package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.UserRepository
import com.titicorp.evcs.model.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(phoneNumber: String, password: String): User {
        return userRepository.login(phoneNumber, password)
    }
}
