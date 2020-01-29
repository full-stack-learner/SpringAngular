package com.server.repository

import com.server.MySpringBootTest
import com.server.repository.client.Client
import com.server.repository.client.ClientRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@MySpringBootTest
class ClientRepositoryTest {

    @Autowired
    private lateinit var clientRepository: ClientRepository

    @Before
    fun setup() {
        clientRepository.deleteAll()
        assertEquals(0, clientRepository.count())
    }

    @After
    fun cleanup() {
        clientRepository.deleteAll()
        assertEquals(0, clientRepository.count())
    }

    @Test
    fun testStoreLoad() {

        val client = Client("secret", scope = listOf("app"), redirectUris = listOf("https://example.com", "http://localhost:4200"))
        assertNotNull(clientRepository.save(client).id)
        assertEquals(1, clientRepository.count())

        val loaded = clientRepository.findByIdOrNull(client.id)
        assertNotNull(loaded)
        assertEquals(client.id, loaded?.id)
        assertEquals(client.redirectUris.size, loaded?.redirectUris?.size)
        assertEquals(client.scope, loaded?.scope)
        assertEquals(client.autoApproveScopes, loaded?.autoApproveScopes)
        assertFalse(loaded?.accessTokenValidity == 0)
        assertTrue(loaded?.refreshTokenValidity == 315569520)
        assertEquals(client.secret, loaded?.secret)
    }
}