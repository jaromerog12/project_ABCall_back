package com.uniandes.project.user.infrastruture.gateway.database.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersEntity : IntIdTable("users") {
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)
    val role = varchar("role", 255)
    val isAccountNonExpired = bool("is_account_non_expired").default(true)
    val isAccountNonLocked = bool("is_account_non_locked").default(true)
    val isCredentialsNonExpired = bool("is_credentials_non_expired").default(true)
    val isEnabled = bool("is_enabled").default(true)
}