package com.server.extensions

import com.server.auth.CustomSimpleGrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request

object TestCreator {

    fun createAuthentication(user: UserDetails, clientId: String): OAuth2Authentication {
        val authorities = listOf(CustomSimpleGrantedAuthority("USER"))
        val params = mapOf(Pair("password", "value"))
        val scope = setOf("app")
        val resourceIds = setOf("resource-id")
        val responseTypes = setOf("GET", "POST")
        val oAuth2Request = OAuth2Request(params, clientId, authorities, true, scope, resourceIds, "https://www.example.com", responseTypes, emptyMap())
        val authenticationToken = UsernamePasswordAuthenticationToken(user, "credential", authorities)
        return OAuth2Authentication(oAuth2Request, authenticationToken)
    }
}