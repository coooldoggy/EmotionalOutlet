package com.coooldoggy.emotionaloutlet

import kotlin.random.Random

data class Message private constructor(
    val user: String,
    val timeMs: Long,
    val text: String,
    val id: Long,
) {
    constructor(
        user: String,
        timeMs: Long,
        text: String,
    ) : this(
        user = user,
        timeMs = timeMs,
        text = text,
        id = Random.nextLong(),
    )
}

data class Friend(
    val name: String,
    val profile: String
)
