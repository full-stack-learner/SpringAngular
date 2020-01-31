package com.server.extensions

import org.springframework.core.env.Environment

/**
 * Returns true if the test profile is currently active, false if not.
 */
val Environment.isTest: Boolean
    get() = activeProfiles.contains("test")