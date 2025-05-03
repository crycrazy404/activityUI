package util.encoders

import org.apache.poi.xslf.usermodel.XSLFSlide

class SlideNotes {
    fun getSlideNotes(slide: XSLFSlide): String {
        val slideNotes = slide.notes
        var notes = ""
        slideNotes?.textParagraphs?.forEach { paragraphList ->
            paragraphList.forEach { paragraph ->
                paragraph.textRuns.forEach { textRun ->
                    notes += textRun.rawText
                }
                notes += "\n"
            }
        }
        return notes
    }
}