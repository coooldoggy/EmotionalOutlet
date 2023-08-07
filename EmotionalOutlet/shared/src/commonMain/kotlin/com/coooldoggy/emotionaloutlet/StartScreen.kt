package com.coooldoggy.emotionaloutlet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.emotionaloutlet.common.onClick
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun StartScreen(onClickChatting: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0XFF888888))) {
        BottomChatBox(modifier = Modifier.align(Alignment.BottomStart), onClickChatting = onClickChatting)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserProfile() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.clip(RoundedCornerShape(50.dp)).size(50.dp).background(Color.White),
            painter = painterResource("trash.png"),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "쓰레기♻️", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomChatBox(modifier: Modifier, onClickChatting: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
            UserProfile()
            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0XFF999999)))
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.onClick { onClickChatting.invoke() },
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource("speech_bubble.png"),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "채팅", color = Color.White, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
