package ru.anipanda.utils

fun String.isValidEmail() = true

val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
fun isEmailValid(email: String): Boolean {
    return EMAIL_REGEX.toRegex().matches(email);
}