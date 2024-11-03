package com.better.betterapp.core.domain.model

object Prompts {
    fun correctSpeakingText(speakingText: String, topic: String): String {
        return """
        The user is discussing the topic: "$topic".
    
        Here is their speaking text:
        "$speakingText"
        
        Please help with the following:

        1. Provide a corrected version of this text, preserving the original meaning but fixing any grammar, coherence, or fluency issues.

        2. Evaluate the text on a scale of 1 to 10 (10 being excellent) for each of the following categories:
           - Coherence
           - Grammar
           - Fluency
        
        Respond strictly in this format:
        
        Corrected Text: [corrected text here]
        Coherence Score: [score]
        Grammar Score: [score]
        Fluency Score: [score]
    """.trimIndent()
    }


    fun generateSpeakingText(topic: String): String {
        return """
            
        """.trimIndent()
    }
}