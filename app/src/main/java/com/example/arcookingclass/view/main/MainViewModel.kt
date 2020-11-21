package com.example.arcookingclass.view.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.DataSource
import com.example.arcookingclass.common.AppDatabase
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = MainViewModel::class.java.simpleName

    private val recipeDao = AppDatabase.instance.recipeDao()
    val recipeList = LivePagedListBuilder<Int, Recipe>(
        recipeDao.getAllRecipeList(), 9
    ).build()

    init {
        generateData()
    }

    private fun generateData() {
        val recipeList: ArrayList<Recipe> = ArrayList()
        recipeList.add(
            Recipe(1, "파전", R.drawable.recipe_1, "Pa", "중급", "35분")
        )
        recipeList.add(
            Recipe(2, "계란말이", R.drawable.recipe_2, "RiceCake", "초급", "25분")
        )
        recipeList.add(
            Recipe(3, "계란찜", R.drawable.recipe_3, "Egg", "중급", "30분")
        )
        recipeList.add(
            Recipe(4, "두부조림", R.drawable.recipe_4, "Bean", "초급", "30분")
        )
        recipeList.add(
            Recipe(5, "어묵볶음", R.drawable.recipe_5, "Beef", "초급", "20분")
        )
        recipeList.add(
            Recipe(6, "감자채볶음", R.drawable.recipe_6, "Noodle", "초급", "25분")
        )
        recipeList.add(
                Recipe(7, "얼큰 수제비", R.drawable.recipe_7, "Noodle", "중급", "40분")
        )
        recipeList.add(
                Recipe(8, "소세지 야채볶음", R.drawable.recipe_8, "Noodle", "초급", "30분")
        )
        recipeList.add(
                Recipe(9, "부대찌개", R.drawable.recipe_9, "Noodle", "초급", "30분")
        )

        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.recipeDao().insertRecipeList(recipeList)
        }
    }

}