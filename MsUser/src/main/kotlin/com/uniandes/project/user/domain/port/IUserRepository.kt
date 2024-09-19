package com.uniandes.project.user.domain.port

import com.uniandes.project.user.domain.model.User
import com.uniandes.project.user.domain.model.UserAuth

interface IUserRepository {
    fun findById(id: Int): UserAuth?
    fun findByUsername(username: String) : UserAuth?
    fun save(user: User): UserAuth
}