package util.encoders

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO

class Base64Encode {

    fun toBase64(bitmap: ImageBitmap): String{

        val awtImage = bitmap.toAwtImage()
        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(awtImage, "PNG", byteArrayOutputStream)

        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())

    }

    fun toImageBitmap(imgString: String): ImageBitmap{
        val bytes = Base64.getDecoder().decode(imgString)

        val bufferedImage = ImageIO.read(ByteArrayInputStream(bytes))

        return bufferedImage.toComposeImageBitmap()
    }

}