package com.uniandes.project.user.domain.utils

import java.security.SecureRandom
import java.util.*


fun createSecretKey(): String {
    val secureRandom: SecureRandom = SecureRandom()
    val secretBytes = ByteArray(36) //36*8=288 (>256 bits required for HS256)
    secureRandom.nextBytes(secretBytes)
    val encoder: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()
    return encoder.encodeToString(secretBytes)
}

