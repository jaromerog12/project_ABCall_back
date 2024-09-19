package com.uniandes.project.user.domain.use_case

import com.uniandes.project.user.application.config.jwt.JwtUtils
import com.uniandes.project.user.domain.model.Login
import com.uniandes.project.user.domain.model.Signup
import com.uniandes.project.user.domain.model.User
import com.uniandes.project.user.domain.model.UserAuth
import com.uniandes.project.user.domain.model.enums.Roles
import com.uniandes.project.user.domain.port.IUserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUseCase(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtils
) {
    fun authenticate(input: Login): Pair<String, Long> {

        return try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(input.username, input.password)
            )
            val userDetails =  userDetailsService.loadUserByUsername(input.username)
            val tokenExpiresIn = jwtUtil.getExpirationTime()
            val userAuthenticated =  UserAuth(
                user = User(
                    username = userDetails.username,
                    password = input.password,
                    role = Roles.valueOf(userDetails.authorities.first().authority.replace("ROLE_",""))
                ), userDetails.authorities)
            val extraClaims = mapOf<String, Any>("role" to userAuthenticated.getRoles())
            val jwtToken = jwtUtil.generateToken(userAuthenticated, extraClaims)
            Pair(jwtToken, tokenExpiresIn)
        }catch (e:Exception){
            e.printStackTrace()
            throw e
        }
    }
}