package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class ObjInputDto(
    val name: String,
    val description: String?
)