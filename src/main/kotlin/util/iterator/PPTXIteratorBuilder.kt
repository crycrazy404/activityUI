package util.iterator

import connection.slideAPI.SlideAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.xslf.usermodel.XMLSlideShow
import util.data.slides.SlideData
import util.dotEnv.ENV
import util.encoders.SlideNotes
import util.encoders.SlideToBitmap
import java.io.FileInputStream
import java.io.IOException

class PPTXIteratorBuilder: Aggregate {
    private var slides: List<SlideData> = emptyList()
    private val converter = SlideToBitmap()
    private val notesParser = SlideNotes()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun loadPresentation(path: String) {
        try {
            FileInputStream(path).use { fis ->
                XMLSlideShow(fis).use { pptx ->
                    slides = pptx.slides.mapIndexed() { index, slide ->
                        SlideData(
                            id = index,
                            header = slide.placeholders.firstOrNull()?.text ?: "Без заголовка",
                            mainPicture = converter.slideScaledImage(slide, 1920, 1080),
                            miniPicture = converter.slideScaledImage(slide, 640, 480),
                            notes = notesParser.getSlideNotes(slide)
                        )
                    }
                }
            }
            scope.launch {
                runCatching {
                    SlideAPI(ENV.apiUrl).sendSlides(slides)
                }.onFailure{
                    println("Server not available")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            slides = emptyList()
        }
    }

    override fun getIterator(): Iterator = PPTXIterator(slides)

    private class PPTXIterator(
        private val slides: List<SlideData>,
        private var currentIndex: Int = 0
    ) : Iterator {

        init {
            require(slides.isNotEmpty()) { "Slides list cannot be empty" }
        }

        override fun getSlide(index: Int): SlideData {
            return when {
                index < 0 -> slides.last()
                index >= slides.size -> slides.first()
                else -> slides[index]
            }
        }

        override fun getSlides(): List<SlideData> = slides

        override fun hasNext(): Boolean = currentIndex < slides.lastIndex

        override fun hasPrev(): Boolean = currentIndex > 0

        override fun next(): Int {
            currentIndex = if (hasNext()) currentIndex + 1 else 0
            return currentIndex
        }

        override fun prev(): Int {
            currentIndex = if (hasPrev()) currentIndex - 1 else slides.lastIndex
            return currentIndex
        }

        override fun getCurrent(): SlideData {
            return slides[currentIndex]
        }


        override fun getCurrentIndex(): Int = currentIndex

        override fun setCurrentIndex(index: Int) {
            currentIndex = index.coerceIn(slides.indices)
        }
    }
}