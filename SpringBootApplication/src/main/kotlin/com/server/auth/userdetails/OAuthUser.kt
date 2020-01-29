package com.server.auth.userdetails

import com.server.auth.CustomSimpleGrantedAuthority
import com.server.repository.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

/**
 * UserDetails implementation of a User as required by Spring UserDetailsService.
 * Make sure to always think about serialization here and keep things as simple as possible,
 * this is serialized into the Authentication object in the database when storing access & refresh tokens.
 * @param user The User to initialize the UserDetails with.
 */
class OAuthUser(user: User) : UserDetails, Serializable {

    companion object {
        private const val serialVersionUID = -1764937456384447645L
    }

    private val username: String = user.username
    private val password: String? = user.password

    private val enabled: Boolean = user.enabled
    private val blocked: Boolean = user.blocked

    private val authorities: List<String> = user.grantedAuthorities

    override fun getAuthorities(): List<GrantedAuthority> {
        return authorities.map { CustomSimpleGrantedAuthority(it) }
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String? {
        return password
    }

    override fun isAccountNonLocked(): Boolean {
        return !blocked
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (other is UserDetails) {
            return username == other.username && password == other.password
        }
        return false
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }
}