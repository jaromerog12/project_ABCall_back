package com.uniandes.project.user.infrastruture.entrypoint

import com.uniandes.project.user.domain.use_case.ValidateTokenUseCase
import org.springframework.web.bind.annotation.*

@RequestMapping("/token")
@RestController
class ValidateTokenController(
    private val validateTokenUseCase: ValidateTokenUseCase
) {

    @PostMapping("/is-valid")
    fun isValidToken(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody validateToken: TokenRequestBody) {
            validateTokenUseCase.execute(authHeader, validateToken.username)
    }

 data class TokenRequestBody(
     val username: String,
 )
}