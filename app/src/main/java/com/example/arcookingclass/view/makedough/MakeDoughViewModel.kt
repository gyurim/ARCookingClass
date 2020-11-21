package com.example.arcookingclass.view.makedough

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.arcookingclass.util.SingleLiveEvent

class MakeDoughViewModel : ViewModel() {
    private val _navigateToNextActivity = SingleLiveEvent<Any>()
    private val _navigateToPrevActivity = SingleLiveEvent<Any>()

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
}