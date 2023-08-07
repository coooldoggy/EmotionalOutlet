package com.coooldoggy.emotionaloutlet

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class ScreenRoute : Parcelable {
    data object Start : ScreenRoute()
    data object Chat : ScreenRoute()
}
