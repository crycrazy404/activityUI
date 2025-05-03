package mainView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import connection.statementsAPI.StatementsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.data.statements.QuestionInputDto
import util.dotEnv.ENV

class Chat {
    private val statementsAPI = StatementsAPI(ENV.apiUrl)

    @Composable
    fun init(slideId: Int) {
        val scope = rememberCoroutineScope()
        var questionsList by remember { mutableStateOf<List<QuestionInputDto>>(emptyList()) }

        LaunchedEffect(slideId) {
            scope.launch {
                questionsList = statementsAPI.getAllQuestionStatementsForSLide(slideId.toString())
            }
        }

        Box(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.LightGray)
                .padding(top = 16.dp)
        ) {
            if (questionsList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Нет вопросов",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(alignment = Alignment.BottomStart),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(questionsList) { question ->
                            ChatMessage().init(question)
                        }
                    }
                }
            }
        }
    }
}