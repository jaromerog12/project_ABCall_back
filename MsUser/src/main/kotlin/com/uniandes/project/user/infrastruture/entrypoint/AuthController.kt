package com.uniandes.project.user.infrastruture.entrypoint

import com.uniandes.project.user.application.config.jwt.JwtUtils
import com.uniandes.project.user.domain.exceptions.RequestBodyException
import com.uniandes.project.user.domain.model.Login
import com.uniandes.project.user.domain.model.Signup
import com.uniandes.project.user.domain.model.UserAuth
import com.uniandes.project.user.domain.model.enums.Roles
import com.uniandes.project.user.domain.use_case.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user/auth/")
@RestController
class AuthController(
    private val loginUseCase: LoginUseCase
) {

    @PostMapping("/login")
    fun authenticate(@RequestBody loginUserDto: LoginUserRequestBody): ResponseEntity<LoginResponse> {
        val (jwtToken, tokenExpiresIn) = loginUseCase.authenticate(loginUserDto.toLogin())
        val loginResponse = LoginResponse(
            token = jwtToken,
            expiresIn = tokenExpiresIn
        )
        return ResponseEntity.ok(loginResponse)
    }

    data class SignupResponseBody(
        val id: Int,
        val username: String,
        val role: String
    )

    data class LoginUserRequestBody(
        val username: String,
        val password: String
    )

    data class LoginResponse(
        val token: String,
        val expiresIn: Long,
    )
}

fun validateRol(rol: String): Roles {
    return try{
        (Roles.valueOf(rol))
    }catch(e: Exception){
        throw RequestBodyException("Nombre del rol no existe")
    }
}

fun AuthController.LoginUserRequestBody.toLogin() = Login(
    username = this.username,
    password = this.password
)
