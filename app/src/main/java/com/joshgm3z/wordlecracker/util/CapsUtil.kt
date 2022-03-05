package com.joshgm3z.wordlecracker.util

import com.joshgm3z.wordlecracker.Word
import java.util.*

class CapsUtil {

    companion object {
        fun convertToCaps(words: List<Word>): List<Word> {
            for (word in words) {
                val uppercase = word.word.uppercase(Locale.getDefault())
                word.word = uppercase
            }
            return words
        }
    }
}