package com.uniandes.project.user.application.config

import com.uniandes.project.user.domain.model.enums.Roles
import com.uniandes.project.user.infrastruture.gateway.database.entities.UsersEntity
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.management.relation.Role
import javax.sql.DataSource

@Component
class DbInitializer {
    @Autowired
    lateinit var dataSource: DataSource

    @PostConstruct
    fun initialize() {
        try {
            Database.connect(dataSource)
            transaction {
                // Creamos las tablas
                SchemaUtils.create(UsersEntity)

                //Insertamos usuario administrador
                if (UsersEntity.selectAll().where( UsersEntity.username eq "SA" ).empty()) {
                    UsersEntity.insert {
                        it[username] = "sa"
                        it[password] = "\$2a\$10\$iMmgf3B4KT0iMHrpjElugOL5rYOPqrGHQAp/oQ7yLHBtYQLroEA8e"
                        it[role] = Roles.ADMIN.name
                    }
                }
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}
