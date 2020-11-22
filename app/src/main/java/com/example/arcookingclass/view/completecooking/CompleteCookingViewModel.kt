package com.example.arcookingclass.view.completecooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arcookingclass.common.AppDatabase
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.util.SingleLiveEvent

class CompleteCookingViewModel : ViewModel() {
    private val _navigateToNextActivity = SingleLiveEvent<Any>()
    private val _navigateToPrevActivity = SingleLiveEvent<Any>()
    private val _startButtonClick : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _pauseButtonClick : MutableLiveData<Boolean> = MutableLiveData(false)
    private val recipeDao = AppDatabase.instance.recipeDao()

    val navigateToNextActivity : LiveData<Any>
        get() = _navigateToNextActivity

    val navigateToPrevActivity : LiveData<Any>
        get() = _navigateToPrevActivity

    fun onNextButtonClick(){
        _navigateToNextActivity.call()
    }

    fun onPrevButtonClick(){
        _navigateToPrevActivity.call()
    }

    fun onStartButtonClick(){
        _startButtonClick.value = true
    }

    fun onPauseButtonClick(){
        _pauseButtonClick.value = true
    }

    fun updateRecipe(recipe : Recipe?){
        if(recipe != null)
            recipeDao.update(recipe)
    }
}