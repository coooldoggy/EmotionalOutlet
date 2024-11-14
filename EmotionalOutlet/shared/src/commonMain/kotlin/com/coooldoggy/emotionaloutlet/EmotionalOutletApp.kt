package com.coooldoggy.emotionaloutlet

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.router.stack.push
import com.coooldoggy.emotionaloutlet.common.AppBar
import com.coooldoggy.emotionaloutlet.common.AppBarBackIcon
import com.coooldoggy.emotionaloutlet.common.TriangleEdgeShape
import com.coooldoggy.emotionaloutlet.common.onClick
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val store = CoroutineScope(SupervisorJob()).createStore()
const val myUser = "Me"

@Composable
fun EmotionalOutletApp() {
    val navigator: Router<ScreenRoute> =
        rememberRouter(ScreenRoute::class, stack = listOf(ScreenRoute.Start))
    RoutedContent(router = navigator) { screen ->
        when (screen) {
            ScreenRoute.Start -> StartScreen(onClickChatting = {
                navigator.push(ScreenRoute.Chat)
            })

            ScreenRoute.Chat -> ChatMain()
        }
    }
}

@Composable
fun ChatMain() {
    val state by store.stateFlow.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                centerContent = { Text("쓰레기♻️") },
                leftContent = { AppBarBackIcon(action = {}) },
            )
        },
    ) { _innerPadding ->
        Box(modifier = Modifier.padding(_innerPadding)) {
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

@OptIn(ExperimentalComposeUiApi::class)
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
            .background(MaterialTheme.colors.background)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                    sendMessage(inputText)
                    inputText = ""
                    true
                } else {
                    false
                }
            },
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
                        inputText = ""
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

enum class AnimationType {
    SCALE, FADE, SLIDE, CROSSFADE
}

@Composable
internal fun Messages(messages: List<Message>) {
    val listState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.size) {
            listState.animateScrollToItem(messages.lastIndex, scrollOffset = 2)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
    ) {
        item { Spacer(Modifier.size(20.dp)) }

        items(messages) { message ->
            val shouldDisappear = remember { mutableStateOf(false) }
            val randomAnimationType = remember { AnimationType.values().random() }

            LaunchedEffect(Unit) {
                delay(3000)
                shouldDisappear.value = true
            }

            ChatMessage(
                message = message,
                shouldDisappear = shouldDisappear.value,
                animationType = randomAnimationType
            )
        }

        item { Box(Modifier.height(70.dp)) }
    }
}

@Composable
fun ChatMessage(message: Message, shouldDisappear: Boolean, animationType: AnimationType) {
    var messageWidth by remember { mutableStateOf(0) } // To store the width of each message
    Crossfade(targetState = shouldDisappear && animationType == AnimationType.CROSSFADE) { isDisappeared ->
        if (!isDisappeared) {
            // Define the animated modifier based on the animation type
            val animatedModifier = when (animationType) {
                AnimationType.SCALE -> Modifier.graphicsLayer(
                    scaleX = animateFloatAsState(if (shouldDisappear) 0f else 1f).value,
                    scaleY = animateFloatAsState(if (shouldDisappear) 0f else 1f).value
                )
                AnimationType.FADE -> Modifier.alpha(
                    animateFloatAsState(if (shouldDisappear) 0f else 1f).value
                )
                AnimationType.SLIDE -> Modifier.offset(
                    x = animateDpAsState(
                        if (shouldDisappear) with(LocalDensity.current) { -messageWidth.toDp() } else 0.dp
                    ).value
                )
                AnimationType.CROSSFADE -> Modifier
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
                    .then(animatedModifier)
                    .onGloballyPositioned { coordinates ->
                        messageWidth = coordinates.size.width // Capture the message width in pixels
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                ChatMessageContent(message)
            }
        }
    }
}

@Composable
fun ChatMessageContent(message: Message) {
    Row(verticalAlignment = Alignment.Bottom) {
        Column {
            Box(
                Modifier
                    .clip(RoundedCornerShape(30.dp, 30.dp, 4.dp, 30.dp))
                    .background(color = ChatColors.MY_MESSAGE)
                    .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
            ) {
                Column( Modifier.padding(5.dp)) {
                    Spacer(Modifier.size(3.dp))
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 18.sp,
                            letterSpacing = 0.sp
                        )
                    )
                    Spacer(Modifier.size(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = timeToString(message.timeMs),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.subtitle1.copy(fontSize = 10.sp),
                            color = ChatColors.TIME_TEXT
                        )
                    }
                }
            }
            Box(Modifier.size(10.dp))
        }
    }
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
