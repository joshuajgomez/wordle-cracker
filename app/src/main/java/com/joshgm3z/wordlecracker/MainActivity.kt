package com.joshgm3z.wordlecracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wordlecracker.domain.WordRepository
import com.joshgm3z.wordlecracker.ui.WordsAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val wordsAdapter: WordsAdapter = WordsAdapter()

    val wordRepository: WordRepository = WordRepository()

    lateinit var etContains: EditText
    lateinit var etNotContains: EditText
    lateinit var etPosition1: EditText
    lateinit var etPosition2: EditText
    lateinit var etPosition3: EditText
    lateinit var etPosition4: EditText
    lateinit var etPosition5: EditText
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        GlobalScope.launch {
            val fetchWords = wordRepository.fetchWords()

            MainScope().launch {
                updateWordsList(fetchWords)
            }
        }
    }

    private fun initUI() {
        val rvWordsList: RecyclerView = findViewById(R.id.rv_words_list)
        rvWordsList.adapter = wordsAdapter
        rvWordsList.layoutManager = LinearLayoutManager(this)

        etContains = findViewById(R.id.et_contains)
        etNotContains = findViewById(R.id.et_not_contains)
        etPosition1 = findViewById(R.id.et_1)
        etPosition2 = findViewById(R.id.et_2)
        etPosition3 = findViewById(R.id.et_3)
        etPosition4 = findViewById(R.id.et_4)
        etPosition5 = findViewById(R.id.et_5)
        progressBar = findViewById(R.id.pb_loading)
        progressBar.visibility = View.VISIBLE

        val btn: Button = findViewById(R.id.btn_search)
        btn.setOnClickListener(this)
    }

    fun updateWordsList(words: List<Word>?) {
        progressBar.visibility = View.INVISIBLE
        wordsAdapter.setWords(words)
    }

    override fun onClick(view: View?) {
        progressBar.visibility = View.VISIBLE

        val listContains = ArrayList<String>()
        val listNotContains = ArrayList<String>()
        val mapPosition = HashMap<Int, Char>()

        val containsText = etContains.text.trim()
        if (!containsText.isEmpty()) {
            val split = containsText.split(" ")
            for (letter in split) {
                listContains.add(letter)
            }
        }

        val notContainsText = etNotContains.text.trim()
        if (!notContainsText.isEmpty()) {
            val split = notContainsText.split(" ")
            for (letter in split) {
                listNotContains.add(letter)
            }
        }

        val text1 = etPosition1.text.trim()
        if (!text1.isEmpty()) {
            mapPosition.set(0, text1[0])
        }
        val text2 = etPosition2.text.trim()
        if (!text2.isEmpty()) {
            mapPosition.set(1, text2[0])
        }
        val text3 = etPosition3.text.trim()
        if (!text3.isEmpty()) {
            mapPosition.set(2, text3[0])
        }
        val text4 = etPosition4.text.trim()
        if (!text4.isEmpty()) {
            mapPosition.set(3, text4[0])
        }
        val text5 = etPosition5.text.trim()
        if (!text5.isEmpty()) {
            mapPosition.set(4, text5[0])
        }

        GlobalScope.launch {
            val filteredWords =
                wordRepository.applyFilter(listContains, listNotContains, mapPosition)

            MainScope().launch {
                updateWordsList(filteredWords)
            }
        }
    }
}