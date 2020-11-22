package com.example.arcookingclass.view.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.arcookingclass.common.AppDatabase
import androidx.paging.LivePagedListBuilder
import com.example.arcookingclass.data.Recipe


class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = MainViewModel::class.java.simpleName
    private val recipeDao = AppDatabase.instance.recipeDao()

    val recipeList = LivePagedListBuilder<Int, Recipe>(
        recipeDao.getAllRecipeList(), 9
    ).build()

}