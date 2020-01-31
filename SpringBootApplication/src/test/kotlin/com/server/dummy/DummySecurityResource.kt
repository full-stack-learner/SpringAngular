package com.server.dummy

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/dummy/secured"])
class DummySecurityResource {

    @RequestMapping(method = [RequestMethod.GET])
    fun get(): String {
        return "Secured"
    }

    @PreAuthorize("#oauth2.hasScope('admin')")
    @RequestMapping(value = ["/admin"], method = [RequestMethod.GET])
    fun getAdmin(): String {
        return "Secured"
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = ["/read"], method = [RequestMethod.GET])
    fun getRead(): String {
        return "Secured"
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @RequestMapping(value = ["/write"], method = [RequestMethod.GET])
    fun getWrite(): String {
        return "Secured"
    }
}