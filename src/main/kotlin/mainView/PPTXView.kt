package mainView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import util.iterator.Iterator

class PPTXView {

    @Composable
    fun init(iter: Iterator){
        val visibleItemsCount = 3 // количество видимых элементов
        val halfVisibleItems = visibleItemsCount / 2 // половина видимых элементов
        var currentIndex by remember { mutableStateOf(iter.getCurrentIndex()) }
        var slideNotes by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(900.dp)
                    .weight(5f)
            ) {
                Box(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                        .background(color = Color.LightGray)
                        .fillMaxHeight()
                        .weight(2f)
                ) {
                    // Основное изображение
                    Image(
                        bitmap = iter.getSlide(currentIndex).mainPicture,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )

                    // Кнопки по бокам
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                currentIndex = iter.prev()
                                slideNotes = iter.getSlide(currentIndex).notes
                            },
                            enabled = iter.hasPrev()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous"
                            )
                        }

                        IconButton(
                            onClick = {
                                currentIndex = iter.next()
                                slideNotes = iter.getSlide(currentIndex).notes
                            },
                            enabled = iter.hasNext()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next"
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Notes().init(iter.getSlide(currentIndex).notes)

                    }
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .weight(2f)
                    ) {
                        Chat().init(currentIndex)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                LazyRow(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                        .background(color = Color.LightGray)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    val startIndex = maxOf(0, currentIndex - halfVisibleItems)
                    val endIndex = minOf(iter.getSlides().size - 1, currentIndex + halfVisibleItems)
                    val indicesToShow = (startIndex..endIndex).map { it.coerceIn(0, iter.getSlides().size - 1) }

                    items(indicesToShow) { index ->
                        val isSelected = index == currentIndex
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.Blue else Color.Transparent
                                )
                                .clickable {
                                    iter.setCurrentIndex(index)
                                    currentIndex = index
                                    slideNotes = iter.getSlide(index).notes
                                }
                        ) {
                            Image(
                                bitmap = iter.getSlide(index).miniPicture,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
