package com.by.sir.max.wordlist.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.by.sir.max.wordlist.entity.Word
import com.by.sir.max.wordlist.repository.Repository
import kotlinx.coroutines.launch

class AddWordViewModel(val repository: Repository) : ViewModel() {
    fun add(string: String) {
        viewModelScope.launch {
            repository.insertWord(Word(string))
        }
    }
}