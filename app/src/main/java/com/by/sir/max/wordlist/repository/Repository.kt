package com.by.sir.max.wordlist.repository

import com.by.sir.max.wordlist.database.WordDatabase
import com.by.sir.max.wordlist.entity.Word

class Repository(val dataSource: WordDatabase) {
    val dao by lazy {
        dataSource.wordDao
    }

    suspend fun insertWord(word: Word) {
        dao.addWord(word)
    }

    suspend fun deleteByWord(word: String) {
        dao.delete(word)
    }

    suspend fun getAll(): List<Word> {
        return dao.getWords()
    }

    suspend fun getReversed():List<Word>{
        return dao.getReverseOrderedWords()
    }

    suspend fun getFirst(n:Int):List<Word>{
        return dao.getFirst(n)
    }

    suspend fun getOrdered():List<Word>{
        return dao.getOrderedWords()
    }

}
