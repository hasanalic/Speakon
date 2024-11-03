package com.better.betterapp.feature_speaking.data

data class CorrectedTextResult(
    val correctedText: String,
    val coheranceScore: Int,
    val grammarScore: Int,
    val fluencyScore: Int
)