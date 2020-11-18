package com.example.arcookingclass.view.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.DataSource
import com.example.arcookingclass.common.AppDatabase
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.arcookingclass.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = MainViewModel::class.java.simpleName

    private val recipeDao = AppDatabase.instance.recipeDao()
    val recipeList = LivePagedListBuilder<Int, Recipe>(
        recipeDao.getAllRecipeList(), 4
    ).build()

    init {
        generateData()
    }

    private fun generateData() {
        val recipeList: ArrayList<Recipe> = ArrayList()
        recipeList.add(
            Recipe(0, "Pajeon", null, "Pa")
        )
        recipeList.add(
            Recipe(1, "Tteokbokki", null, "RiceCake")
        )
        recipeList.add(
            Recipe(2, "EggRoll", null, "Egg")
        )
        recipeList.add(
            Recipe(3, "KongGukSu", null, "Bean")
        )
        recipeList.add(
            Recipe(4, "Bulgogi", null, "Beef")
        )
        recipeList.add(
            Recipe(5, "Japchae", null, "Noodle")
        )

        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.recipeDao().insertRecipeList(recipeList)
        }
    }

}