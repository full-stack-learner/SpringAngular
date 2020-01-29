package com.server.repository.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.server.auth.userdetails.OAuthUser
import com.server.repository.MongoObject
import org.springframework.security.core.userdetails.UserDetails

class User(var username: String, var password: String, var name: String? = null, var enabled: Boolean = true, var blocked: Boolean = false) : MongoObject() {

    @JsonIgnore
    var grantedAuthorities: List<String> = listOf()

    @get:JsonIgnore
    val oAuth: UserDetails
        get() = OAuthUser(this)

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return username == other.username && password == other.password && enabled == other.enabled && blocked == other.blocked && grantedAuthorities == other.grantedAuthorities
        }
        return false
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + blocked.hashCode()
        result = 31 * result + grantedAuthorities.hashCode()
        return result
    }
}