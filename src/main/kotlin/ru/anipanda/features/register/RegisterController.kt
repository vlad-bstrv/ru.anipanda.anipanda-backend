package ru.anipanda.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.anipanda.database.tokens.TokenDto
import ru.anipanda.database.tokens.Tokens
import ru.anipanda.database.users.UserDto
import ru.anipanda.database.users.Users
import ru.anipanda.utils.isValidEmail
import java.util.*

class RegisterController(val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if(!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDto = Users.fetchUser(registerReceiveRemote.login)

        if (userDto != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(UserDto(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password,
                    email = registerReceiveRemote.email,
                    username = ""
                ))
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            Tokens.insert(
                TokenDto(
                    rowId = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.login,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }




    }
}