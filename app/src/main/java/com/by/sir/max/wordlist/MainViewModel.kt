package com.by.sir.max.wordlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.by.sir.max.wordlist.entity.Word
import com.by.sir.max.wordlist.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository) : ViewModel() {
    val list by lazy { MutableLiveData<List<Word>>(emptyList()) }

    init {
        getWords()
    }

    fun addWord(string: String) {
        viewModelScope.launch {
            repository.insertWord(Word(word = string))
        }
    }

    fun getWords() {
        viewModelScope.launch {
            list.postValue(repository.getAll())
        }
    }

    fun deleteWord(string: String) {
        viewModelScope.launch {
            repository.deleteByWord(string)
        }
    }

    fun getFirstWords(n: Int) {
        viewModelScope.launch {
            list.postValue(repository.getFirst(n))
        }
    }

    fun getReversedWords() {
        viewModelScope.launch {
            list.postValue(repository.getReversed())
        }
    }

    fun getSortedWords() = viewModelScope.launch {
        list.postValue(repository.getOrdered())
    }

}