package com.example.arcookingclass.common

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.data.RecipeDao

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

    companion object {
        private const val TAG = "AppDatabase"
        val instance = Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java, "arcookingclass.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}