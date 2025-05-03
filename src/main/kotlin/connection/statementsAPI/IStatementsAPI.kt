package connection.statementsAPI

import util.data.statements.*

interface IStatementsAPI {

    suspend fun getAllStatements(): List<StatementsDto>
    suspend fun getStatementsByVerb(verbId: String) : List<StatementsDto>
    suspend fun getStatementByUserId(userId: Long): List<StatementsDto>
    suspend fun getAllQuestionStatementsForSLide(slideId: String): List<QuestionInputDto>
    suspend fun getAllRatesForSlide(slideId: String): SlideRateDto
    suspend fun getSlidesStats(): List<SlideStatsDto>


}