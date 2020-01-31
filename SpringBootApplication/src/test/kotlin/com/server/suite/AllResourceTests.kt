package com.server.suite

import com.server.resources.OAuthSecurityTest
import com.server.resources.OAuthTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(OAuthTest::class, OAuthSecurityTest::class)
class AllResourceTests