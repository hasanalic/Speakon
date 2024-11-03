package com.better.betterapp.feature_magic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MagicViewModel (
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _stateMagic = mutableStateOf(MagicState())
    val stateMagic: State<MagicState> = _stateMagic

    init {
        val funId = savedStateHandle.get<Int>("funId") ?: 0
        val topic = savedStateHandle.get<String>("topic") ?: ""
        val speakingText = savedStateHandle.get<String>("speakingText") ?: ""
        val aiCorrectedText = savedStateHandle.get<String>("aiCorrectedText") ?: ""

        if (funId == 1) {
            generateSpeakingFromScratch(topic)
        } else {
            suggestSomeWordsAndPhrases(topic, speakingText)
        }
        println(funId)
        println(topic)
        println(speakingText)
        println(aiCorrectedText)
    }


    private fun generateSpeakingFromScratch(topic: String) {
        _stateMagic.value = _stateMagic.value.copy(isLoading = true)

        viewModelScope.launch {
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "AIzaSyDcWEwm6iaPPN1Pdb_5GYbkYshjjU77hQo"
            )

            val prompt = """
                Topic: "$topic"
    
                Please write a speaking text about the topic provided above. The response should be approximately one paragraph long, coherent, and suitable for an English-speaking exercise. Keep the tone conversational and clear.
            """.trimIndent()

            try {
                val response = generativeModel.generateContent(prompt)

                _stateMagic.value = _stateMagic.value.copy(
                    isLoading = false,
                    text = response.text ?: "Bir hata meydana geldi. Lütfen tekrar dene."
                )
            } catch (e: Exception) {
                _stateMagic.value = _stateMagic.value.copy(
                    isLoading = false,
                    text = "Bir hata meydana geldi: ${e.message}"
                )
            }
        }
    }

    private fun suggestSomeWordsAndPhrases(topic: String, speakingText: String) {
        _stateMagic.value = _stateMagic.value.copy(isLoading = true)

        viewModelScope.launch {
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "AIzaSyDcWEwm6iaPPN1Pdb_5GYbkYshjjU77hQo"
            )

            val prompt = """
            The user is discussing the topic: "$topic". Here is their speaking text:
            
            "$speakingText"
            
            Based on this speaking text, please provide the following:
            
            1. **Vocabulary Suggestions**: List specific words related to the topic that would add depth and sophistication to the text.
            2. **Phrase Suggestions**: Provide impactful phrases or expressions that would enhance the speaking’s clarity and engagement.
            3. **Idiomatic Expressions**: Recommend relevant idioms or common sayings that would make the text sound more fluent and natural.

            Format the response exactly as follows:
            
            Vocabulary Suggestions:
            - [word1, word2, word3, ...]

            Phrase Suggestions:
            - "phrase1"
            - "phrase2"
            - "phrase3"
            - ...

            Idiomatic Expressions:
            - "idiom1"
            - "idiom2"
            - "idiom3"
            - ...
            
            Please ensure each suggestion fits naturally with the topic and context.
        """.trimIndent()

            try {
                val response = generativeModel.generateContent(prompt)

                _stateMagic.value = _stateMagic.value.copy(
                    isLoading = false,
                    text = response.text ?: "Bir hata meydana geldi. Lütfen tekrar dene."
                )
            } catch (e: Exception) {
                _stateMagic.value = _stateMagic.value.copy(
                    isLoading = false,
                    text = "Bir hata meydana geldi: ${e.message}"
                )
            }
        }
    }
}