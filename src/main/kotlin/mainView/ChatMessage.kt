package mainView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import util.data.statements.QuestionInputDto

class ChatMessage {
    @Composable
    fun init(question: QuestionInputDto) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(
                    text = "${question.name} ${question.surname}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = question.question,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}