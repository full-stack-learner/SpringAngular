package com.server.resources

import com.server.repository.client.Client
import com.server.repository.client.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/client"])
class ClientResource {

    @Autowired
    private lateinit var clientRepository: ClientRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @GetMapping(value = ["/all"])
    fun list(): List<Client> {
        return clientRepository.findAll()
    }

    @PostMapping
    fun create(@RequestBody client: Client): ResponseEntity<Client> {
        client.secret = passwordEncoder.encode(client.secret)
        return ResponseEntity.ok(clientRepository.save(client))
    }
}