package com.example.arcookingclass.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe WHERE id ORDER BY id COLLATE NOCASE ASC")
    fun getAllRecipeList() : androidx.paging.DataSource.Factory<Int, Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeList(recipeList : List<Recipe>)
}