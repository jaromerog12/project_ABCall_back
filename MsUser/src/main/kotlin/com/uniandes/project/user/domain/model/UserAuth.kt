package com.uniandes.project.user.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAuth(
    private val user: User,
    private val authorities: Collection<GrantedAuthority> = emptyList()
) : UserDetails {

    fun getId() = user.id

    fun getRoles() = user.role

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return user.isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return user.isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return user.isEnabled
    }
}