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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.emotionaloutlet.common.AppBar
import com.coooldoggy.emotionaloutlet.common.AppBarBackIcon
import com.coooldoggy.emotionaloutlet.common.TriangleEdgeShape
import com.coooldoggy.emotionaloutlet.common.onClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.skottie.Logger

val store = CoroutineScope(SupervisorJob()).createStore()
val myUser = "Me"

@Composable
fun EmotionalOutletApp() {
    Scaffold(
        topBar = {
            AppBar(
                centerContent = { Text("쓰레기♻️") },
                leftContent = { AppBarBackIcon(action = {}) },
            )
        },
    ) { _innerPadding ->
        Box(modifier = Modifier.padding(_innerPadding)) {
            ChatMain()
        }
    }
}

@Composable
fun ChatMain() {
    val state by store.stateFlow.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(Modifier.weight(1f)) {
                Messages(state.messages)
            }
            SendMessage(
                sendMessage = ::sendMessage,
                inputMessage = state.inputMessage,
                onMessageChanged = ::onMessageChanged,
            )
        }
    }
}

private fun sendMessage(text: String) {
    store.send(
        Action.SendMessage(
            Message(myUser, timeMs = timestampMs(), text),
        ),
    )
}

private fun onMessageChanged(text: String) {
    store.send(
        Action.InputMessage(
            message = text,
        ),
    )
}

@Composable
fun SendMessage(
    sendMessage: (String) -> Unit,
    inputMessage: String,
    onMessageChanged: (String) -> Unit,
) {
    var inputText by remember { mutableStateOf(inputMessage) }
    com.coooldoggy.emotionaloutlet.common.BasicTextField(
        modifier = Modifier.fillMaxWidth()
            .height(42.dp)
            .background(MaterialTheme.colors.background),
//            .onKeyEvent { event ->
//                if (event.key == Key.Enter) {
//                    sendMessage(inputText)
//                    true
//                } else {
//                    false
//                }
//            },
        value = inputText,
        valueChangeListener = {
            inputText = it
            onMessageChanged.invoke(inputText)
        },
        rightContent = {
            Row(
                modifier = Modifier
                    .onClick {
                        sendMessage(inputText)
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val backgroundColor = if (inputText.isEmpty()) Color.LightGray else Color.Yellow
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        .background(color = backgroundColor).size(width = 50.dp, height = 35.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("전송")
                }
            }
        },
    )
}

@Composable
internal inline fun Messages(messages: List<Message>) {
    val listState = rememberLazyListState()
    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.last()) {
            listState.animateScrollToItem(messages.lastIndex, scrollOffset = 2)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
    ) {
        item { Spacer(Modifier.size(20.dp)) }
        items(messages, key = { it.id }) {
            ChatMessage(isMyMessage = true, it)
        }
        item {
            Box(Modifier.height(70.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserPic() {
    val imageSize = 48f
    val painter = painterResource("trash.png")
    Image(
        modifier = Modifier
            .size(imageSize.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = "User picture",
    )
}

@Composable
inline fun ChatMessage(isMyMessage: Boolean, message: Message) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMyMessage) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (!isMyMessage) {
                Column {
                    UserPic()
                }
                Spacer(Modifier.size(2.dp))
                Column {
                    Triangle(true, ChatColors.OTHERS_MESSAGE)
                }
            }

            Column {
                Box(
                    Modifier.clip(
                        RoundedCornerShape(
                            10.dp,
                            10.dp,
                            if (!isMyMessage) 10.dp else 0.dp,
                            if (!isMyMessage) 0.dp else 10.dp,
                        ),
                    )
                        .background(color = if (!isMyMessage) ChatColors.OTHERS_MESSAGE else ChatColors.MY_MESSAGE)
                        .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
                ) {
                    Column {
                        if (!isMyMessage) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = message.user,
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 0.sp,
                                        fontSize = 14.sp,
                                    ),
                                    color = Color(0xFFEA3468),
                                )
                            }
                        }
                        Spacer(Modifier.size(3.dp))
                        Text(
                            text = message.text,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 18.sp,
                                letterSpacing = 0.sp,
                            ),
                        )
                        Spacer(Modifier.size(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.align(Alignment.End),
                        ) {
                            Text(
                                text = timeToString(message.timeMs),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.subtitle1.copy(fontSize = 10.sp),
                                color = ChatColors.TIME_TEXT,
                            )
                        }
                    }
                }
                Box(Modifier.size(10.dp))
            }
            if (isMyMessage) {
                Column {
                    Triangle(false, ChatColors.MY_MESSAGE)
                }
            }
        }
    }
}

@Composable
fun Triangle(risingToTheRight: Boolean, background: Color) {
    Box(
        Modifier
            .padding(bottom = 10.dp, start = 0.dp)
            .clip(TriangleEdgeShape(risingToTheRight))
            .background(background)
            .size(6.dp),
    )
}
