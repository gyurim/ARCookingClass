package com.example.arcookingclass.view.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.arcookingclass.util.SingleLiveEvent

class IngredientViewModel : ViewModel() {
    private val _navigateToActivity = SingleLiveEvent<Any>()
    val navigateToActivity : LiveData<Any>
        get() = _navigateToActivity

    fun onButtonClick(){
        _navigateToActivity.call()
    }
}