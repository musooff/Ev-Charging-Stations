package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.UserRepository
import com.titicorp.evcs.model.User
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(name: String, phoneNumber: String, password: String): User {
        return userRepository.register(name, phoneNumber, password)
    }
}
