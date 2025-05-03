package util.data.slides

import androidx.compose.ui.graphics.ImageBitmap
import util.encoders.Base64Encode

data class SlideData(
    var id: Int,
    var header: String,
    var mainPicture: ImageBitmap,
    var miniPicture: ImageBitmap,
    var notes: String,
)
{
    fun toSlideInputData() =  SlideInputData(
        header = this.header,
        mainPicture = Base64Encode().toBase64(this.mainPicture),
        miniPicture = Base64Encode().toBase64(this.miniPicture),
        notes = this.notes
    )
}
