package com.by.sir.max.wordlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.by.sir.max.wordlist.entity.Word

@Database(entities = [Word::class], version = 1, exportSchema = true)
abstract class WordDatabase() : RoomDatabase() {
    abstract val wordDao: WordDao

    companion object {
        @Volatile
        var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context):WordDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}