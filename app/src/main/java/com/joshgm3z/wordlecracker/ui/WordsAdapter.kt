package com.joshgm3z.wordlecracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wordlecracker.R
import com.joshgm3z.wordlecracker.Word

class WordsAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private var wordList: List<Word>? = null

    fun setWords(words: List<Word>?) {
        this.wordList = words
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.setData(wordList!![position])
    }

    override fun getItemCount(): Int {
        return wordList?.size ?: 0
    }

}