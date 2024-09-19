package com.uniandes.project.user.application.config.exception

import com.uniandes.project.user.domain.exceptions.RequestBodyException
import com.uniandes.project.user.domain.exceptions.UsernameOrPasswordIncorrectException
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class GlobalErrorHandlerException {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorDetails> {
        ex.printStackTrace()
        val errorDetails = ErrorDetails(
            timestamp = LocalDateTime.now(),
            message = ex.message ?: "Usuario o contraseña incorrecta",
        )
        return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UsernameOrPasswordIncorrectException::class)
    fun handleUsernameOrPasswordIncorrectException(ex: UsernameOrPasswordIncorrectException, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            timestamp = LocalDateTime.now(),
            message = ex.message ?: "Usuario o contraseña incorrecta",
        )
        return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException::class)
    fun handleJdbcSQLIntegrityConstraintViolationException(ex: JdbcSQLIntegrityConstraintViolationException, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            timestamp = LocalDateTime.now(),
            message = ex.message ?: "Usuario ya se encuentra registrado",
        )
        return ResponseEntity(errorDetails, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(RequestBodyException::class)
    fun handleRequestBodyException(ex: RequestBodyException, request: WebRequest): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            timestamp = LocalDateTime.now(),
            message = ex.message ?: "Error en el request",
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorDetails(
    val timestamp: LocalDateTime,
    val message: String,
)