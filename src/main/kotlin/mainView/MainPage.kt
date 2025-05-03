package mainView

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import statsView.SlideStatsView
import util.fileChouser.FileSelector
import util.iterator.Iterator
import util.iterator.PPTXIteratorBuilder

class MainPage {
    @Composable
    fun init() {
        val loader = remember { PPTXIteratorBuilder() }
        val iter = remember { mutableStateOf<Iterator?>(null) }
        val errorMessage = remember { mutableStateOf<String?>(null) }
        val fileSelected = remember { mutableStateOf(false) }

        // Состояние для вкладок
        val tabs = listOf("Просмотр", "Настройки") // Пример вкладок
        var selectedTab by remember { mutableStateOf(0) }

        if (!fileSelected.value) {
            // Экран выбора файла
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            try {
                                val filePath = FileSelector().selectFile("Выбор PPTX", "pptx")
                                loader.loadPresentation(filePath)
                                iter.value = loader.getIterator()
                                errorMessage.value = null
                                fileSelected.value = true
                            } catch (e: Exception) {
                                errorMessage.value = "Ошибка: ${e.message}"
                                e.printStackTrace()
                            }
                        }
                    ) {
                        Text("Выбрать файл презентации")
                    }

                    errorMessage.value?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        } else {
            // Основной интерфейс с вкладками
            Column {
                // Вкладки
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                // Содержимое вкладок
                when (selectedTab) {
                    0 -> { // Вкладка просмотра
                        iter.value?.let { iterator ->
                            PPTXView().init(iterator)
                        }
                    }
                    1 -> { // Вкладка настроек
                        iter.value?.let { iterator ->
                            SlideStatsView().init(iterator.getSlides())
                        }
                    }
                }
            }
        }
    }
}