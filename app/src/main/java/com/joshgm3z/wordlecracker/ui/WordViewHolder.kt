package com.joshgm3z.wordlecracker.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wordlecracker.R
import com.joshgm3z.wordlecracker.Word

class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tvWord: TextView = itemView.findViewById(R.id.tv_word)

    fun setData(word: Word) {
        tvWord.text = word.word
    }

}