package com.uniandes.project.user.domain.use_case

import com.uniandes.project.user.domain.model.Signup
import com.uniandes.project.user.domain.model.User
import com.uniandes.project.user.domain.model.UserAuth
import com.uniandes.project.user.domain.port.IUserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignupUseCase(
    private val userRepository: IUserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun signup(input: Signup): UserAuth {
        val user = User(
            username = input.username,
            password = bCryptPasswordEncoder.encode(input.password),
            role = input.role
        )
        return userRepository.save(user)
    }
}