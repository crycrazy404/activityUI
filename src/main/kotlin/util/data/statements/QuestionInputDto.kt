package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class QuestionInputDto(
    val name: String,
    val surname : String,
    val question : String,
)
