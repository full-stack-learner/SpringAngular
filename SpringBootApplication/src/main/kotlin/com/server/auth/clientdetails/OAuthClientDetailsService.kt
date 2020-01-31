package com.server.auth.clientdetails

import com.server.auth.Scope
import com.server.repository.client.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.client.BaseClientDetails

class OAuthClientDetailsService : ClientDetailsService {

    companion object {
        /** default client that is always present */
        private val defaultClient: ClientDetails by lazy {
            val client = BaseClientDetails()
            client.clientId = "5e051ea44f64347c8530c268"
            client.setScope(listOf(Scope.Admin).map { it.string })
            client.setAuthorizedGrantTypes(listOf("password", "authorization_code", "refresh_token"))
            client.registeredRedirectUri = listOf("https://www.example.com",
                    "http://46.101.159.55:4200",
                    "http://46.101.159.55:80",
                    "http://46.101.159.55",
                    "http://localhost:4200",
                    "http://localhost:4200/").toSet()
            client.accessTokenValiditySeconds = 3600
            client
        }
    }

    @Autowired
    private lateinit var clientRepository: ClientRepository

    override fun loadClientByClientId(clientId: String?): ClientDetails {
        val client = clientRepository.findByIdOrNull(clientId)
        if (client == null) {
            if (clientId == defaultClient.clientId) {
                return defaultClient
            }
            throw ClientRegistrationException("No client found for id $clientId")
        } else {
            return client.oAuth
        }
    }
}