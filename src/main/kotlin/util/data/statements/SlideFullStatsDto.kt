package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class SlideFullStatsDto(
    val slideID: String,
    val rates: SlideRateDto,
    val questions: List<QuestionInputDto>
)