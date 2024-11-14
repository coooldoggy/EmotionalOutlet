package windowsizeclass

import androidx.compose.ui.unit.DpSize
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

@Suppress("ConvertSecondaryConstructorToPrimary") // To mirror android api
actual class WindowSizeClass actual constructor(
    actual val widthSizeClass: WindowWidthSizeClass,
    actual val heightSizeClass: WindowHeightSizeClass
) {
    companion object {
        @OptIn(ExperimentalForeignApi::class)
        fun calculateFromSize(size: DpSize): WindowSizeClass {
            // Calculate the appropriate size class based on screen dimensions
            val screenWidth = UIScreen.mainScreen.bounds.useContents { size.width }
            val screenHeight = UIScreen.mainScreen.bounds.useContents { size.height }

            val windowWidthSizeClass = CommonWindowWidthSizeClass.fromWidth(screenWidth)
            val windowHeightSizeClass = CommonWindowHeightSizeClass.fromHeight(screenHeight)

            return WindowSizeClass(windowWidthSizeClass, windowHeightSizeClass)
        }
    }
}