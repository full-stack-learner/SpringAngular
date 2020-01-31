package com.server.config

import com.server.extensions.isTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter


@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var environment: Environment

    /**
     * All resources that are generally open and don't require authentication.
     */
    protected val openResources: Array<String>
        get() {
            return if (environment.isTest) {
                arrayOf("/api/open", "/api/dummy/open")
            } else {
                arrayOf("/api/open")
            }
        }

    override fun configure(http: HttpSecurity) {
        http.requestMatchers().antMatchers("/me").and()
                .requestMatchers().antMatchers("/api/**").and()
                .authorizeRequests().antMatchers(*openResources).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/user").permitAll().and()
                .authorizeRequests().anyRequest().authenticated();
    }
}