package ru.anipanda.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.anipanda.database.tokens.TokenDto
import ru.anipanda.database.tokens.Tokens
import ru.anipanda.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDto = Users.fetchUser(receive.login)

        if (userDto == null) {
            call.respond(HttpStatusCode.BadRequest, "User not Found")
        } else {
            if (userDto.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDto(
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }

    }
}