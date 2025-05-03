package connection.slideAPI

import connection.KtorHttpClient
import connection.safeApiCall
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.serialization.json.Json
import util.data.slides.SlideData


class SlideAPI(private val baseURL: String) : ISlideAPI {

    override suspend fun sendSlides(slides: List<SlideData>) {
        println("SEnd slide called")
        safeApiCall(baseURL) { client ->
            println("SEnd slide start execute")
            val slidesPretty = slides.map { slide -> slide.toSlideInputData() }
            val responseBody = Json.encodeToString(slidesPretty)
            client.post("$baseURL/slides/set") {
                contentType(ContentType.Application.Json)
                setBody(responseBody)
            }.body<Unit>()
            println("SEnd slide end execute")

        }
    }
}