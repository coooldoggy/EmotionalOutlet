package windowsizeclass

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.WindowState
import windowsizeclass.CommonWindowSizeClass.Companion.calculateFromSize

@Composable
fun calculateWindowSizeClass(windowState: WindowState): WindowSizeClass = calculateFromSize(windowState.size)
