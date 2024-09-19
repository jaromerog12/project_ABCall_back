package com.uniandes.project.user.domain.model

import com.uniandes.project.user.domain.model.enums.Roles


data class User (
    val id: Int = 0,
    val username: String,
    val password: String,
    val role: Roles,
    val isAccountNonExpired: Boolean = true,
    val isAccountNonLocked: Boolean = true,
    val isCredentialsNonExpired: Boolean = true,
    val isEnabled: Boolean = true
)
