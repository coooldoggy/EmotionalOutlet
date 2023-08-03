package com.coooldoggy.emotionaloutlet.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppBar(
    leftContent: (@Composable () -> Unit)? = null,
    centerContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color.White,
    clickListener: (() -> Unit) = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = backgroundColor)
            .onClick {
                clickListener.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        centerContent?.let {
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                centerContent()
            }
        }

        leftContent?.let {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leftContent()
            }
        }

        rightContent?.let {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                rightContent()
            }
        }
    }
}

@Composable
fun AppBarBackIcon(
    action: () -> Unit,
) {
    AppBarDefaultIcon(
        iconClickListener = {
            action.invoke()
        },
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppBarDefaultIcon(
    iconClickListener: (() -> Unit)? = null,
) {
    Image(
        modifier = Modifier
            .padding(start = 4.dp, end = 16.dp)
            .size(26.dp)
            .onClick {
                iconClickListener?.invoke()
            },
        painter = painterResource(res = "back.png"),
        contentDescription = "",
    )
}