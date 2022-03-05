package com.joshgm3z.wordlecracker.domain

import android.util.Log
import com.joshgm3z.wordlecracker.Word
import com.joshgm3z.wordlecracker.retrofit.WordService
import com.joshgm3z.wordlecracker.util.CapsUtil
import com.joshgm3z.wordlecracker.util.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import kotlin.collections.HashMap

class WordRepository {

    private val URL: String = "https://api.datamuse.com"

    lateinit var allWords: List<Word>

    private val wordService: WordService = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WordService::class.java)

    suspend fun fetchWords(): List<Word>? {
        val words = fetchOnline(Const.REGEX_ALL)
        Log.println(Log.ASSERT, "fetchWords", "words = " + words?.size)
        allWords = words!!
        return words
    }

    suspend fun applyFilter(
        listContains: List<String>,
        listNotContains: List<String>,
        mapPosition: HashMap<Int, Char>
    ): List<Word> {
        var words = ArrayList<Word>()
        for (word in allWords) {
            val text = word.word
            var isMatching = true

            for (letter in listContains) {
                if (!text.contains(letter)) {
                    isMatching = false
                    break
                }
            }
            if (!isMatching) continue

            for (letter in listNotContains) {
                if (text.contains(letter)) {
                    isMatching = false
                    break
                }
            }
            if (!isMatching) continue

            val keys = ArrayList(mapPosition.keys)
            for (key in keys) {
                val letter1: Char = text[key]
                val letter2: Char? = mapPosition[key]
                if (letter1 != letter2) {
                    isMatching = false
                    break
                }
            }
            if (!isMatching) continue

            words.add(Word(text))
        }
        return words
    }

    suspend fun applyFilterOnline(
        listContains: List<String>,
        listNotContains: List<String>,
        mapPosition: HashMap<Int, Char>
    ): List<Word> {
        var bufferWords = ArrayList<Word>()
        if (listNotContains.isNotEmpty()) {
            var regexNotContains = StringBuilder("[^0-9")
            for (letter in listNotContains) {
                regexNotContains.append(letter)
            }
            regexNotContains.append("]{${Const.WORD_LENGTH}}")
            bufferWords = ArrayList(fetchOnline(regexNotContains.toString()))
        } else {
            bufferWords = ArrayList(allWords)
        }
        val out = ArrayList<Word>()
        for (word in bufferWords) {
            val text = word.word
            var isMatching = true

            for (letter in listContains) {
                if (!text.contains(letter)) {
                    isMatching = false
                    break
                }
            }
            if (!isMatching) continue

            val keys = ArrayList(mapPosition.keys)
            for (key in keys) {
                val letter1: Char = text[key]
                val letter2: Char? = mapPosition[key]
                if (letter1 != letter2) {
                    isMatching = false
                    break
                }
            }
            if (!isMatching) continue

            out.add(Word(text))
        }
        return out
    }

    private fun fetchOnline(regex: String): List<Word>? {
        var words: List<Word>? = wordService.getWords(
            regex, Const.MAX_RESULTS
        ).execute().body()
        words = words?.let { CapsUtil.convertToCaps(it) }
        return words
    }

}