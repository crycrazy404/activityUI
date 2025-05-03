package connection

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


object KtorHttpClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend fun getClientIfAlive(baseUrl: String): HttpClient? {
        return try {
            println("Trying to connect to $baseUrl/isAlive")
            val response = client.get("$baseUrl/isAlive")
            println("Response status: ${response.status}")
            if (response.status == HttpStatusCode.OK) {
                println("Server is alive")
                client
            } else {
                println("Server returned non-OK status")
                null
            }
        } catch (e: Exception) {
            println("Connection failed: ${e.javaClass.simpleName}: ${e.message}")
            null
        }
    }
    fun close() {
        CoroutineScope(Dispatchers.IO).launch {
            client.close()
        }
    }
}
