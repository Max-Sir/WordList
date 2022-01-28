package com.by.sir.max.wordlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.by.sir.max.wordlist.R
import com.by.sir.max.wordlist.database.WordDatabase
import com.by.sir.max.wordlist.databinding.ActivityAddWordBinding
import com.by.sir.max.wordlist.repository.Repository

class AddWordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddWordBinding
    private lateinit var repository: Repository
    private lateinit var database: WordDatabase
    private lateinit var viewModel: AddWordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = WordDatabase.getInstance(this)
        repository = Repository(database)
        viewModel = AddWordViewModel(repository)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_word)
        binding.apply {
            button.setOnClickListener {
                textToAddToWords.text?.toString()?.let{
                    viewModel.add(it)
                }
                finish()
            }
        }
    }
}