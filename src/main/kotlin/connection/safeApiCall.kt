package connection

import io.ktor.client.*


suspend fun <T> safeApiCall(
    baseUrl: String,
    block: suspend (HttpClient) -> T
): T? {
    // 1. Получаем клиент
    val client = try {
        KtorHttpClient.getClientIfAlive(baseUrl).also {
            if (it == null) println("Debug: getClientIfAlive returned null")
        }
    } catch (e: Exception) {
        println("DEBUG: Failed to get client - ${e.javaClass.simpleName}: ${e.message}")
        return null
    } ?: return null.also { println("DEBUG: Client is null") }

    // 2. Выполняем запрос
    return try {
        block(client).also {
            println("DEBUG: API call succeeded")
        }
    } catch (e: Exception) {
        println("DEBUG: API call failed - ${e.javaClass.simpleName}: ${e.message}")
        null
    }
}