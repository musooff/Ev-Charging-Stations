package com.titicorp.evcs.network.model

sealed interface Auth {
    data class LoginRequest(
        val phoneNumber: String,
        val password: String,
    ) : Auth

    data class RegisterRequest(
        val name: String,
        val phoneNumber: String,
        val password: String,
    ) : Auth

    data class Result(
        val phoneNumber: String,
        val name: String,
    ) : Auth
}
