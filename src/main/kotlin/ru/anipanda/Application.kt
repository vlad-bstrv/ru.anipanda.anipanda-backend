package ru.anipanda

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.anipanda.features.login.configureLoginRouting
import ru.anipanda.features.register.configureRegisterRouting
import ru.anipanda.plugins.*

fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/anipanda", driver = "org.postgresql.Driver",
        user = "postgres", password = "SafikcPL6sxfDn")
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureSerialization()
    }.start(wait = true)
}
