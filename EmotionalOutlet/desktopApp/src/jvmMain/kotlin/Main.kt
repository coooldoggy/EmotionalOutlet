import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.singleWindowApplication
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.coooldoggy.emotionaloutlet.EmotionalOutletApp
import io.github.xxfast.decompose.LocalComponentContext
import windowsizeclass.LocalWindowSizeClass
import windowsizeclass.WindowSizeClass
import windowsizeclass.calculateWindowSizeClass

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
    application {
        val windowState: WindowState = rememberWindowState()
        val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(windowState)
        LifecycleController(lifecycle, windowState)

        CompositionLocalProvider(
            LocalComponentContext provides rootComponentContext,
            LocalWindowSizeClass provides windowSizeClass,
        ) {
            singleWindowApplication(
                title = "감정쓰레기통",
                state = WindowState(size = DpSize(500.dp, 800.dp)),
            ) {
                EmotionalOutletApp()
            }
        }
    }
}
