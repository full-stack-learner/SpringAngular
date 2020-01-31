package com.server.auth

/**
 * Enum representation of OAuth scopes.
 */
enum class Scope(val string: String) {
    Admin("admin"),
    Read("read"),
    Write("write")
}