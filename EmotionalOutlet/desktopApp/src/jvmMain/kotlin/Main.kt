import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.coooldoggy.emotionaloutlet.EmotionalOutletApp

fun main() =
    singleWindowApplication(
        title = "감정쓰레기통",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        EmotionalOutletApp()
    }
