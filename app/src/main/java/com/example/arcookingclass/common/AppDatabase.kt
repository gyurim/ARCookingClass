package com.example.arcookingclass.common

import android.telecom.Call
import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.data.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao

    companion object {
        private const val TAG = "AppDatabase"
        val instance = Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java, "arcookingclass.db")
                .addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        CoroutineScope(Dispatchers.IO).launch {
                            generateData()
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

}

fun generateData()  {
    val recipeList:ArrayList<Recipe> = ArrayList()
    recipeList.add(
            Recipe(1, "파전", R.drawable.recipe_1, "Pa", "중급", "35분", false)
    )
    recipeList.add(
            Recipe(2, "계란말이", R.drawable.recipe_2, "RiceCake", "초급", "25분", false)
    )
    recipeList.add(
            Recipe(3, "계란찜", R.drawable.recipe_3, "Egg", "중급", "30분", false)
    )
    recipeList.add(
            Recipe(4, "두부조림", R.drawable.recipe_4, "Bean", "초급", "30분", false)
    )
    recipeList.add(
            Recipe(5, "어묵볶음", R.drawable.recipe_5, "Beef", "초급", "20분", false)
    )
    recipeList.add(
            Recipe(6, "감자채볶음", R.drawable.recipe_6, "Noodle", "초급", "25분", false)
    )
    recipeList.add(
            Recipe(7, "얼큰 수제비", R.drawable.recipe_7, "Noodle", "중급", "40분", false)
    )
    recipeList.add(
            Recipe(8, "소세지 야채볶음", R.drawable.recipe_8, "Noodle", "초급", "30분", false)
    )
    recipeList.add(
            Recipe(9, "부대찌개", R.drawable.recipe_9, "Noodle", "초급", "30분", false)
    )
    AppDatabase.instance.recipeDao().insertRecipeList(recipeList)
}