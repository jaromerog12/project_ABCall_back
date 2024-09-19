package com.uniandes.project.user.domain.use_case

import com.uniandes.project.user.application.config.jwt.JwtUtils
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class ValidateTokenUseCase(
    private val userDetailsService: UserDetailsService,
    private val jwtUtils: JwtUtils
) {

    fun execute(token: String, username: String): Boolean {
        val userAuth = userDetailsService.loadUserByUsername(username)

        val jwt = token.substring(7)

        val isValid = jwtUtils.isTokenValid(jwt, userAuth)
        return isValid
    }
}