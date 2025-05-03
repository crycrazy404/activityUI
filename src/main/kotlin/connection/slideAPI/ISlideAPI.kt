package connection.slideAPI

import util.data.slides.SlideData

interface ISlideAPI {

    suspend fun sendSlides(slides: List<SlideData>)



}