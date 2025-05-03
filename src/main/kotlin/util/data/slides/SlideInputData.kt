package util.data.slides

import kotlinx.serialization.Serializable

@Serializable
data class SlideInputData(
    val header: String,
    val mainPicture: String,
    val miniPicture: String,
    val notes: String,
)