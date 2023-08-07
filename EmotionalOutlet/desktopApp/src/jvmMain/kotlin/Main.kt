import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.coooldoggy.emotionaloutlet.EmotionalOutletApp
import io.github.xxfast.decompose.LocalComponentContext
import windowsizeclass.LocalWindowSizeClass
import windowsizeclass.WindowSizeClass
import windowsizeclass.calculateWindowSizeClass

fun main() {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
    application {
        val windowState: WindowState = rememberWindowState(size = DpSize(500.dp, 800.dp))
        val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(windowState)
        LifecycleController(lifecycle, windowState)

        Window(
            title = "감정쓰레기통",
            state = windowState,
            onCloseRequest = { exitApplication() },
        ) {
            CompositionLocalProvider(
                LocalComponentContext provides rootComponentContext,
                LocalWindowSizeClass provides windowSizeClass,
            ) {
                EmotionalOutletApp()
            }
        }
    }
}
