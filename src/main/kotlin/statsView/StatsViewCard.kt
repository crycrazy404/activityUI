package statsView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.data.slides.SlideData
import util.data.statements.SlideStatsDto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatsViewCard(
    slide: SlideData,
    stat: SlideStatsDto,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                bitmap = slide.miniPicture,
                contentDescription = "Превью слайда",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Text(
                text = slide.header,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Divider(thickness = 1.dp, color = MaterialTheme.colors.background)

            Column {
                StatItem().init("Просмотры", stat.requestCount)
                Spacer(modifier = Modifier.height(4.dp))
                StatItem().init("Средняя оценка", stat.averageRate)
                Spacer(modifier = Modifier.height(4.dp))
                StatItem().init("Вопросы", stat.questionCount)
            }
        }
    }
}
