package util.data.statements

import kotlinx.serialization.Serializable

@Serializable
data class SlideStatsDto(

    val slideID: String,
    val requestCount: String,
    val averageRate: String,
    val questionCount: String,

)