package com.uniandes.project.user.application.config.security

import com.uniandes.project.user.domain.exceptions.UsernameOrPasswordIncorrectException
import com.uniandes.project.user.domain.port.IUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class UserDetailsServiceConfiguration(
    private val userRepository: IUserRepository
) {

    @Bean
    fun userDetailService(): UserDetailsService {
        return UserDetailsService { username: String ->
            val user = userRepository.findByUsername(username)
            user ?: throw UsernameOrPasswordIncorrectException("Usuario o contraseña incorrecta")
        }
    }
}