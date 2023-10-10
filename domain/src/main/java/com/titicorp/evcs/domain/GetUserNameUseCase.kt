package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.UserRepository
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): String {
        return userRepository.getUserName()
    }
}
