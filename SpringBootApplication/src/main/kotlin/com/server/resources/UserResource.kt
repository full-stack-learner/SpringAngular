package com.server.resources

import com.mongodb.MongoClient
import com.server.repository.user.User
import com.server.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/user"])
class UserResource {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @GetMapping(value = ["/all"])
    fun list(): List<User> {
        return userRepository.findAll()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) {
        userRepository.deleteById(id)
    }

    @PostMapping
    fun create(@RequestBody user: User): ResponseEntity<User> {
        if (userRepository.findByUsername(user.username) == null) {
            user.password = passwordEncoder.encode(user.password)
            return ResponseEntity.ok(userRepository.save(user))
        }
        return ResponseEntity.badRequest().build()
    }
}