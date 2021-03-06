package com.joshgm3z.wordlecracker.retrofit

import com.joshgm3z.wordlecracker.Word
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordService {

    // URL : "api.datamuse.com/words?sp=[a-zA-Z]{5}"

    @GET("words")
    fun getWords(
        @Query("sp") regex: String,
        @Query("max") max: Int,
    ): Call<List<Word>>
}