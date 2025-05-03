package connection.statementsAPI

import connection.KtorHttpClient
import connection.safeApiCall
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import util.data.statements.*

class StatementsAPI(private val baseURL: String) : IStatementsAPI {

    private val currentRoot = "$baseURL/statement"

    override suspend fun getAllStatements(): List<StatementsDto> {
        return safeApiCall(baseURL) { client ->
            client.get("$baseURL/statements/all") {
                accept(ContentType.Application.Json)
            }.body<List<StatementsDto>>()
        } ?: emptyList()
    }

    override suspend fun getStatementsByVerb(verbId: String): List<StatementsDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getStatementByUserId(userId: Long): List<StatementsDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllQuestionStatementsForSLide(slideId: String): List<QuestionInputDto> {
        val slide = slideId.removePrefix("slide_")
        return safeApiCall(baseURL) { client ->
            client.get("$currentRoot/get/questions/for/slide/$slide")
                .body<List<QuestionInputDto>>()
        } ?: emptyList()
    }

    override suspend fun getAllRatesForSlide(slideId: String): SlideRateDto {
        val slide = slideId.removePrefix("slide_")
        return safeApiCall(baseURL) { client ->
           client.get("$currentRoot/get/rates/for/slide/$slide").body<SlideRateDto>()
        }?: SlideRateDto("0","0","0","0", "0","0")
    }

    override suspend fun getSlidesStats(): List<SlideStatsDto> {
        return safeApiCall(baseURL) { client ->
            client.get("$currentRoot/get/slide/stats").body<List<SlideStatsDto>>()
        } ?: emptyList()
    }

}
