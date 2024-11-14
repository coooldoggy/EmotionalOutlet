package windowsizeclass

actual value class WindowWidthSizeClass private actual constructor(actual val value: Int)
actual object WindowHeightSizeClasses {
    actual val Compact: WindowHeightSizeClass = WindowHeightSizeClass.Compact
    actual val Medium: WindowHeightSizeClass = WindowHeightSizeClass.Medium
    actual val Expanded: WindowHeightSizeClass = WindowHeightSizeClass.Expanded
}