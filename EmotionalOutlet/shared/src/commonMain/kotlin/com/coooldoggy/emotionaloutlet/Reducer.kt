package com.coooldoggy.emotionaloutlet

sealed interface Action {
    data class SendMessage(val message: Message) : Action
    data class InputMessage(val message: String) : Action
}

data class State(
    val messages: List<Message> = emptyList(),
    val inputMessage: String = "",
)

fun chatReducer(state: State, action: Action): State =
    when (action) {
        is Action.SendMessage -> {
            state.copy(
                messages = (state.messages + action.message).takeLast(100),
            )
        }
        is Action.InputMessage -> {
            state.copy(
                inputMessage = action.message,
            )
        }
    }
