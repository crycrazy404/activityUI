import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import connection.KtorHttpClient
import connection.statementsAPI.StatementsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mainView.MainPage
import util.dotEnv.ENV

@Composable
@Preview
fun App() {

    MainPage().init()

}

fun main() = application {
    Window(onCloseRequest = {
        KtorHttpClient.close()
        exitApplication()
    }) {
        App()
    }
}
