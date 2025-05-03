package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class SlideRateDto(
    val ones: String,
    val twos: String,
    val threes: String,
    val fours: String,
    val fives: String,
    val average: String,
)
