package com.example.common

import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.ranges.random

object IdGenerator {
    fun generateCustomId(length: Int = 13): String {
        val chars = ('A'..'Z')
        val randomPart = (1..(length - 2)).map { chars.random() }.joinToString("")
        return "DF$randomPart"
    }
}