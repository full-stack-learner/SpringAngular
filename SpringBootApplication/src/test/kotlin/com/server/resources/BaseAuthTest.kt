package com.server.resources

import com.server.auth.Scope
import com.server.repository.auth.token.AccessTokenRepository
import com.server.repository.auth.token.RefreshTokenRepository
import com.server.repository.client.Client
import com.server.repository.client.ClientRepository
import com.server.repository.user.User
import com.server.repository.user.UserRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class BaseAuthTest : BaseResourceTest() {

    /** Convenience getter that creates a Basic Authorization header (Base64 encoded cliendId & secret) */
    protected val basicAuthHeader: RequestPostProcessor
        get() = SecurityMockMvcRequestPostProcessors.httpBasic(clientId, clientSecret)

    protected val username = "user@user.com"
    protected val password = "123456"
    protected val clientId = "5e051ea44f64347c8530c264"
    protected val clientSecret = "client-secret"
    protected val redirectUri = "https://www.example.com"

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var clientRepository: ClientRepository

    @Autowired
    protected lateinit var accessTokenRepository: AccessTokenRepository

    @Autowired
    protected lateinit var refreshTokenRepository: RefreshTokenRepository

    @Autowired
    protected lateinit var passwordEncoder: PasswordEncoder

    @Before
    override fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()

        userRepository.deleteAll()
        clientRepository.deleteAll()
        accessTokenRepository.deleteAll()
        refreshTokenRepository.deleteAll()
        assertEquals(0, accessTokenRepository.count())
        assertEquals(0, refreshTokenRepository.count())

        val user = User(username, passwordEncoder.encode(password))
        assertNotNull(userRepository.save(user))
        assertEquals(1, userRepository.count())

        val client = Client(passwordEncoder.encode(clientSecret),
                scope = listOf(Scope.Admin).map { it.string },
                grantTypes = listOf("password", "refresh_token", "client_credentials", "authorization_code"),
                redirectUris = listOf(redirectUri, "http://localhost:4200"))
        client.id = clientId
        assertNotNull(clientRepository.save(client))
        assertEquals(1, clientRepository.count())
    }

    @After
    fun cleanup() {
        userRepository.deleteAll()
        clientRepository.deleteAll()
        accessTokenRepository.deleteAll()
        refreshTokenRepository.deleteAll()
    }

    /**
     * Obtains access & refresh token via grant_type password.
     */
    protected fun obtainTokensWithGrantTypePassword(): Pair<String, String> {
        return obtainTokensWithGrantTypePassword(clientId, clientSecret)
    }

    /**
     * Obtains access & refresh token via grant_type password using the given client.
     */
    @Throws(Exception::class)
    protected fun obtainTokensWithGrantTypePassword(clientId: String, clientSecret: String): Pair<String, String> {

        val header = SecurityMockMvcRequestPostProcessors.httpBasic(clientId, clientSecret)

        val result = mvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .param("grant_type", "password")
                .param("client_id", clientId)
                .param("username", username)
                .param("password", password)
                .with(header)
                .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.scope").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.expires_in").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh_token").exists()).andReturn()

        val resultString = result.response.contentAsString
        val jsonParser = JacksonJsonParser()
        return Pair(jsonParser.parseMap(resultString)["access_token"].toString(), jsonParser.parseMap(resultString)["refresh_token"].toString())
    }
}