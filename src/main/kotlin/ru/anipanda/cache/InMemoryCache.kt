package ru.anipanda.cache

import ru.anipanda.features.register.RegisterReceiveRemote

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}

data class TokenCache(
    val login: String,
    val token: String
)