package util.encoders

import org.apache.poi.xslf.usermodel.XSLFSlide
import org.apache.poi.xslf.usermodel.XSLFTextShape
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.apache.batik.anim.dom.SVGDOMImplementation
import org.w3c.dom.Document
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.ByteArrayInputStream


private const val DEFAULT_SLIDE_WIDTH = 960
private const val DEFAULT_SLIDE_HEIGHT = 540

class SlideToBitmap {
    fun slideScaledImage(slide: XSLFSlide, targetWidth: Int, targetHeight: Int): ImageBitmap {

        val svgImpl = SVGDOMImplementation.getDOMImplementation()
        val svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI
        val document: Document = svgImpl.createDocument(svgNS, "svg", null)


        val svgGraphics = SVGGraphics2D(document).apply {
            val scaleX = targetWidth.toDouble() / DEFAULT_SLIDE_WIDTH
            val scaleY = targetHeight.toDouble() / DEFAULT_SLIDE_HEIGHT
            scale(scaleX, scaleY)
            svgCanvasSize = Dimension(targetWidth, targetHeight)
        }


        optimizeTextShapes(slide)


        slide.draw(svgGraphics)


        val pngOutputStream = ByteArrayOutputStream()
        val svgOutputStream = ByteArrayOutputStream()

        try {
            svgGraphics.stream(svgOutputStream.writer(), true)

            val svgInputStream = ByteArrayInputStream(svgOutputStream.toByteArray())
            val pngTranscoder = PNGTranscoder().apply {
                addTranscodingHint(PNGTranscoder.KEY_WIDTH, targetWidth.toFloat())
                addTranscodingHint(PNGTranscoder.KEY_HEIGHT, targetHeight.toFloat())
            }

            pngTranscoder.transcode(
                TranscoderInput(svgInputStream),
                TranscoderOutput(pngOutputStream)
            )

            return ImageIO.read(ByteArrayInputStream(pngOutputStream.toByteArray()))
                .toComposeImageBitmap()
        } finally {
            svgOutputStream.close()
            pngOutputStream.close()
        }
    }

    private fun optimizeTextShapes(slide: XSLFSlide) {
        slide.shapes.asSequence()
            .filterIsInstance<XSLFTextShape>()
            .forEach { shape ->
                shape.textParagraphs.forEach { paragraph ->
                    paragraph.textRuns.forEach { run ->
                        run.fontSize *= 0.8
                        run.characterSpacing *= 0.8
                    }
                }
            }
    }
}