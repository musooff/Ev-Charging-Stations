package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(phoneNumber: String, password: String) {
        return userRepository.login(phoneNumber, password)
    }
}
