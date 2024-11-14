package windowsizeclass

import androidx.compose.ui.unit.DpSize

@Suppress("ConvertSecondaryConstructorToPrimary") // To mirror android api
actual class WindowSizeClass actual constructor(
    actual val widthSizeClass: WindowWidthSizeClass,
    actual val heightSizeClass: WindowHeightSizeClass
) {
    companion object {
        fun calculateFromSize(size: DpSize): WindowSizeClass {
            val windowWidthSizeClass = CommonWindowWidthSizeClass.fromWidth(size.width)
            val windowHeightSizeClass = CommonWindowHeightSizeClass.fromHeight(size.height)
            return WindowSizeClass(windowWidthSizeClass, windowHeightSizeClass)
        }
    }
}