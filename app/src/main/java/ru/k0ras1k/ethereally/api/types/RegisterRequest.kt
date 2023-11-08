package ru.k0ras1k.ethereally.api.types

class RegisterRequest (
    val email: String,
    val phone_number: String,
    val first_name: String,
    val last_name: String,
    val password: String
)