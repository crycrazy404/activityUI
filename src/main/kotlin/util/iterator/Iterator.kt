package util.iterator

import util.data.slides.SlideData

interface Iterator {

    fun getSlide(index: Int): SlideData
    fun getSlides(): List<SlideData>
    fun hasNext(): Boolean
    fun hasPrev(): Boolean
    fun next(): Int
    fun prev(): Int
    fun getCurrent(): SlideData
    fun getCurrentIndex(): Int
    fun setCurrentIndex(index: Int)

}