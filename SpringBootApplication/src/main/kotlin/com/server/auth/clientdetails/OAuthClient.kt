package com.server.auth.clientdetails

import com.server.auth.CustomSimpleGrantedAuthority
import com.server.repository.client.Client
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import java.io.Serializable

/**
 * ClientDetails implementation of a User as required by Spring ClientDetailsService.
 * @param client The Client to initialize the ClientDetails with.
 */
class OAuthClient(client: Client) : ClientDetails, Serializable {

    companion object {
        private const val serialVersionUID = -2918540686806255887L
    }

    private val id: String = client.id
    private val secret: String? = client.secret
    private val accessTokenValidity: Int = client.accessTokenValidity
    private val refreshTokenValidity: Int? = client.refreshTokenValidity

    private val resources: List<String> = client.resources
    private val redirectUris: List<String> = client.redirectUris

    private val scope: List<String> = client.scope
    private val autoApproveScopes: List<String> = client.autoApproveScopes

    private val grantTypes: List<String> = client.grantTypes
    private val grantedAuthorities: List<String> = client.grantedAuthorities

    override fun getClientId(): String {
        return id
    }

    override fun getClientSecret(): String? {
        return secret
    }

    override fun isSecretRequired(): Boolean {
        return !secret.isNullOrEmpty()
    }

    override fun getAdditionalInformation(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun getAccessTokenValiditySeconds(): Int {
        return accessTokenValidity
    }

    override fun getResourceIds(): MutableSet<String> {
        return resources.toMutableSet()
    }

    override fun isAutoApprove(scope: String?): Boolean {
        if (scope.isNullOrEmpty()) {
            return false
        }
        return autoApproveScopes.contains(scope)
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        return grantedAuthorities.map { CustomSimpleGrantedAuthority(it) }
    }

    override fun getRefreshTokenValiditySeconds(): Int {
        return refreshTokenValidity ?: 0 // 0 means non-expiring
    }

    override fun getRegisteredRedirectUri(): MutableSet<String> {
        return redirectUris.toMutableSet()
    }

    override fun isScoped(): Boolean {
        return scope.isNotEmpty()
    }

    override fun getScope(): MutableSet<String> {
        return scope.toMutableSet()
    }

    override fun getAuthorizedGrantTypes(): MutableSet<String> {
        return grantTypes.toMutableSet()
    }

    override fun equals(other: Any?): Boolean {
        if (other is ClientDetails) {
            return id == other.clientId && secret == other.clientSecret
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (secret?.hashCode() ?: 0)
        return result
    }
}