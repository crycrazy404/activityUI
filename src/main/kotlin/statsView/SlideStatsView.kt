package statsView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import connection.statementsAPI.StatementsAPI
import androidx.compose.ui.platform.LocalWindowInfo
import util.data.slides.SlideData
import util.data.statements.QuestionInputDto
import util.data.statements.SlideFullStatsDto
import util.data.statements.SlideRateDto
import util.data.statements.SlideStatsDto
import util.dotEnv.ENV

class SlideStatsView {

    @Composable
    fun init(
        slides: List<SlideData>,
        modifier: Modifier = Modifier
    ) {
        val slideStats = remember { mutableStateOf<List<SlideStatsDto>>(emptyList()) }
        val isLoading = remember { mutableStateOf(false) }
        val selectedSlide = remember { mutableStateOf<SlideStatsDto?>(null) }
        val statementsAPI = StatementsAPI(ENV.apiUrl)
        LaunchedEffect(Unit) {
            isLoading.value = true
            try {
                slideStats.value = statementsAPI.getSlidesStats()
            } catch (e: Exception) {
                println("Ошибка получения статистики: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }

        Box(modifier = modifier.fillMaxSize()) {
            if (isLoading.value) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(slides) { slide ->
                    val stat = slideStats.value.find { it.slideID == "slide_${slide.id}" }
                        ?: SlideStatsDto(
                            slideID = "slide_${slides.indexOf(slide)}",
                            requestCount = "0",
                            averageRate = "0",
                            questionCount = "0"
                        )

                    StatsViewCard(
                        slide = slide,
                        stat = stat,
                        onClick = { selectedSlide.value = stat },
                    )
                }
            }

            selectedSlide.value?.let { slide ->
                val slideQuestions = mutableStateOf<List<QuestionInputDto>?>(null)
                val slideRate = mutableStateOf<SlideRateDto?>(null)
                LaunchedEffect(slide.slideID) {
                    try{
                        slideQuestions.value = statementsAPI.getAllQuestionStatementsForSLide(slide.slideID)
                        println(statementsAPI.getAllQuestionStatementsForSLide(slide.slideID))
                        slideRate.value = statementsAPI.getAllRatesForSlide(slide.slideID)
                        println(statementsAPI.getAllRatesForSlide(slide.slideID))
                        println("zxczxczxc")
                    }catch (e: Exception){
                        println(e.message)
                    }

                }
                AlertDialog(
                    onDismissRequest = { selectedSlide.value = null },
                    title = { Text("Детали статистики") },
                    text = {
                        Column {
                            Text("Слайд: ${slide.slideID}")
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            StatItem().init("Средняя оценка", (slideRate.value?.average ?: 0f).toString()) // предполагая, что это Float
                            println(slideQuestions.toString())
                            StatItem().init("Количество вопросов", (slideQuestions.value?.size ?: 0).toString())
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { selectedSlide.value = null }) {
                            Text("Закрыть")
                        }
                    }
                )
            }
        }
    }
}