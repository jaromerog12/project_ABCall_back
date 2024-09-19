package com.uniandes.project.user.infrastruture.gateway.database

import com.uniandes.project.user.domain.model.User
import com.uniandes.project.user.domain.model.UserAuth
import com.uniandes.project.user.domain.model.enums.Roles
import com.uniandes.project.user.domain.port.IUserRepository
import com.uniandes.project.user.infrastruture.gateway.database.entities.UsersEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Repository

@Repository
class UserRepository: IUserRepository {
    override fun findById(id: Int): UserAuth? {
        return transaction {
            val query = (UsersEntity
                .selectAll()
                .where(UsersEntity.id eq id)
                .map {
                    val user = it.toUser()

                    val authorities = listOf(SimpleGrantedAuthority("ROLE_" + user.role.name))
                    UserAuth(user, authorities)
                })

            query.firstOrNull()
        }
    }

    override fun findByUsername(username: String): UserAuth? {
        return transaction {
            val query = (UsersEntity
                .selectAll()
                .where(UsersEntity.username eq username)
                .map {
                    val user = it.toUser()

                    val authorities = listOf(SimpleGrantedAuthority("ROLE_" + user.role.name))
                    UserAuth(user, authorities)
                })

            query.firstOrNull()
        }
    }

    override fun save(user: User): UserAuth {
        return  transaction {
            val id =UsersEntity.insertAndGetId {
                it[this.username] = user.username
                it[this.password] = user.password
                it[this.role] = user.role.name
            }

            findById(id.value)!!
        }
    }
}

internal fun ResultRow.toUser() = User (
    id = this[UsersEntity.id].value,
    username = this[UsersEntity.username],
    password = this[UsersEntity.password],
    role = Roles.valueOf(this[UsersEntity.role]),
    isAccountNonExpired = this[UsersEntity.isAccountNonExpired],
    isAccountNonLocked = this[UsersEntity.isAccountNonLocked],
    isCredentialsNonExpired = this[UsersEntity.isCredentialsNonExpired],
    isEnabled = this[UsersEntity.isEnabled]
)