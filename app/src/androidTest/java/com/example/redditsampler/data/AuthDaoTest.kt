package com.example.redditsampler.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProductDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var authDao: AuthDao
    private lateinit var accessToken : String

    val authResponse = AuthResponse(0, accessToken,
        "temporary", 3600, "read", "a67IFIEJF30v07f0FYf-fhhf_fufejeshdisfHOUGHGS9743593523")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking<Unit> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        authDao = database.authDao()
        database.authDao().insertAuth(authResponse)
    }

    @Test
    fun getAuthToken() {
        val authResponse = database.authDao().getAuthToken()
        assertTrue(authResponse.equals(accessToken))
    }

    @Test
    fun getAuthorization() {
        val authResponses : List<AuthResponse> = database.authDao().getAuthorization()
        assertTrue(authResponses.size > 0)
    }

}