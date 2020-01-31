package com.server.resources

import com.server.auth.Scope
import com.server.repository.client.Client
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class OAuthSecurityTest : BaseAuthTest() {

    private val baseUrlDummyOpen = "/api/dummy/open"
    private val baseUrlDummySecured = "/api/dummy/secured"

    @Test
    fun testOpenEndpoint() {
        mvc.perform(get(baseUrlDummyOpen)).andExpect(status().isOk)
    }

    @Test
    fun testSecuredEndpointError() {
        mvc.perform(get(baseUrlDummySecured)).andExpect(status().isUnauthorized)
    }

    @Test
    fun testSecuredEndpointAuthorized() {
        val (at, _) = obtainTokensWithGrantTypePassword()

        mvc.perform(get(baseUrlDummySecured).header("Authorization", "Bearer bad-access-token")).andExpect(status().isUnauthorized)
        mvc.perform(get(baseUrlDummySecured).header("Authorization", "Bearer $at")).andExpect(status().isOk)
    }

    @Test
    fun testAdminScope() {

        val client = createClient(Scope.Admin)

        val (at, _) = obtainTokensWithGrantTypePassword(client.id, clientSecret)
        mvc.perform(get("$baseUrlDummySecured/admin").header("Authorization", "Bearer $at")).andExpect(status().isOk)

        mvc.perform(get("$baseUrlDummySecured/read").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
        mvc.perform(get("$baseUrlDummySecured/write").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
    }

    @Test
    fun testWriteScope() {

        val client = createClient(Scope.Write)

        val (at, _) = obtainTokensWithGrantTypePassword(client.id, clientSecret)
        mvc.perform(get("$baseUrlDummySecured/write").header("Authorization", "Bearer $at")).andExpect(status().isOk)

        mvc.perform(get("$baseUrlDummySecured/read").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
        mvc.perform(get("$baseUrlDummySecured//admin").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
    }

    @Test
    fun testReadScope() {

        val client = createClient(Scope.Read)

        val (at, _) = obtainTokensWithGrantTypePassword(client.id, clientSecret)
        mvc.perform(get("$baseUrlDummySecured//read").header("Authorization", "Bearer $at")).andExpect(status().isOk)

        mvc.perform(get("$baseUrlDummySecured//write").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
        mvc.perform(get("$baseUrlDummySecured//admin").header("Authorization", "Bearer $at")).andExpect(status().isForbidden)
    }

    private fun createClient(scope: Scope): Client {
        val client = Client(passwordEncoder.encode(clientSecret),
                scope = listOf(scope).map { it.string },
                grantTypes = listOf("password", "refresh_token"),
                redirectUris = listOf(redirectUri))

        assertNotNull(clientRepository.save(client))
        return client
    }
}