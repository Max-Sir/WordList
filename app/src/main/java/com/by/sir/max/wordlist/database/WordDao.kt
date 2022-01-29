package com.by.sir.max.wordlist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.by.sir.max.wordlist.entity.Word

@Dao
interface WordDao {

    @Insert(onConflict = REPLACE)
    suspend fun addWord(word: Word)

    @Query("DELETE FROM Word WHERE word = :word")
    suspend fun delete(word: String)

    @Query("SELECT * FROM Word")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM Word ORDER BY word ASC LIMIT :n")
    suspend fun getFirst(n: Int): List<Word>

    @Query("SELECT * FROM Word ORDER BY word DESC")
    suspend fun getReverseOrderedWords(): List<Word>

    @Query("SELECT * FROM Word ORDER BY word ASC")
    suspend fun getOrderedWords(): List<Word>

    @Query("UPDATE Word SET word =:newWord WHERE word = :oldWord")
    suspend fun updateNote(oldWord: String, newWord: String)
}