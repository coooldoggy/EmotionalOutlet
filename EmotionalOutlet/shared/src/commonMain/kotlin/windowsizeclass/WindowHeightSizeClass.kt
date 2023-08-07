package windowsizeclass

expect value class WindowHeightSizeClass private constructor(private val value: Int)

expect object WindowHeightSizeClasses {
  val Compact: WindowHeightSizeClass
  val Medium: WindowHeightSizeClass
  val Expanded: WindowHeightSizeClass
}
