package com.uniandes.project.user.domain.model

import com.uniandes.project.user.domain.model.enums.Roles

data class Signup (
    val username: String,
    val password: String,
    val role: Roles
)