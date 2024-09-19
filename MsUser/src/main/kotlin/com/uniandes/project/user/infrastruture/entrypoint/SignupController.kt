package com.uniandes.project.user.infrastruture.entrypoint

import com.uniandes.project.user.domain.model.Signup
import com.uniandes.project.user.domain.model.UserAuth
import com.uniandes.project.user.domain.model.enums.Roles
import com.uniandes.project.user.domain.use_case.SignupUseCase
import com.uniandes.project.user.infrastruture.entrypoint.AuthController.SignupResponseBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class SignupController(
    private val signupUseCase: SignupUseCase,
) {

    @PostMapping("/signup")
    fun register(@RequestBody registerUserDto: RegisterUserRequestBody): ResponseEntity<SignupResponseBody> {

        val role = validateRol(registerUserDto.role)
        val userAuth = signupUseCase.signup(registerUserDto.toSignup(role))
        return ResponseEntity(userAuth.toSignupResponse(), HttpStatus.CREATED)
    }

    data class RegisterUserRequestBody(
        val username: String,
        val password: String,
        val role: String
    )
}

fun SignupController.RegisterUserRequestBody.toSignup(role: Roles) = Signup(
    username = this.username,
    password = this.password,
    role = role
)

fun UserAuth.toSignupResponse() = SignupResponseBody(
    id = this.getId(),
    username = this.username,
    role = this.getRoles().name
)