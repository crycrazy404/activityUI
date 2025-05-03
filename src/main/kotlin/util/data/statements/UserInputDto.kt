package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class UserInputDto(
    val name: String,
    val surname: String,
    val lastName: String,
)