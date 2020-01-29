package com.server.repository.client

import com.fasterxml.jackson.annotation.JsonIgnore
import com.server.auth.clientdetails.OAuthClient
import com.server.repository.MongoObject
import org.springframework.security.oauth2.provider.ClientDetails

class Client(var secret: String?,
             /** The time in seconds the access tokens of the client are valid, default = 3600 (1 hour) */
             val accessTokenValidity: Int = 3600,
             /** The time in seconds the refresh tokens of the client are valid, default = 315569520 / 10 years (0 or null = never expires) */
             val refreshTokenValidity: Int? = 315569520,
             val resources: List<String> = listOf(),
             val redirectUris: List<String> = listOf(),
             val scope: List<String>,
             val autoApproveScopes: List<String> = listOf(),
             val grantTypes: List<String> = listOf(),
             val grantedAuthorities: List<String> = listOf()) : MongoObject() {

    @get:JsonIgnore
    val oAuth: ClientDetails
        get() = OAuthClient(this)

    override fun equals(other: Any?): Boolean {
        if (other is Client) {
            return id == other.id && secret == other.secret
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (secret?.hashCode() ?: 0)
        return result
    }
}