package statsView

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.data.statements.SlideRateDto

class ExpandedRateItem {
    @Composable
    fun init(
        label: String,
        value: String,
        ratingData: SlideRateDto,
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$label: $value",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Свернуть" else "Развернуть"
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    RatingBarChart(ratingData)

                    RatingDetails(ratingData)
                }
            }
        }
    }

    @Composable
    private fun RatingBarChart(data: SlideRateDto) {
        val counts = listOf(
            data.ones.toIntOrNull() ?: 0,
            data.twos.toIntOrNull() ?: 0,
            data.threes.toIntOrNull() ?: 0,
            data.fours.toIntOrNull() ?: 0,
            data.fives.toIntOrNull() ?: 0
        )
        val maxCount = (counts.maxOrNull() ?: 0).coerceAtLeast(1)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            counts.forEachIndexed { index, count ->
                val color = when (index) {
                    0 -> Color(0xFFEF5350)
                    1 -> Color(0xFFFFA726)
                    2 -> Color(0xFFFFEE58)
                    3 -> Color(0xFFA5D6A7)
                    4 -> Color(0xFF2E7D32)
                    else -> Color.Gray
                }

                val barHeight = 80.dp * (count.toFloat() / maxCount)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .width(16.dp)
                            .height(barHeight)
                            .background(color, shape = RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${index + 1}",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    private fun RatingDetails(data: SlideRateDto) {
        Column(modifier = Modifier.padding(8.dp)) {
            RatingDetailItem("1 звезда", data.ones)
            RatingDetailItem("2 звезды", data.twos)
            RatingDetailItem("3 звезды", data.threes)
            RatingDetailItem("4 звезды", data.fours)
            RatingDetailItem("5 звезд", data.fives)
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            RatingDetailItem("Средняя оценка", data.average, isBold = true)
        }
    }

    @Composable
    private fun RatingDetailItem(label: String, value: String, isBold: Boolean = false) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
