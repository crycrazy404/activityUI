package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class StatementsDto(
    val actor: UserInputDto,
    val verb: String,
    val obj: ObjInputDto,
    val result: String?
)