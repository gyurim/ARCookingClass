package com.example.arcookingclass.view.placedough

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arcookingclass.util.SingleLiveEvent

class PlaceDoughViewModel : ViewModel() {
    private val _navigateToNextActivity = SingleLiveEvent<Any>()
    private val _navigateToPrevActivity = SingleLiveEvent<Any>()
    private val _startButtonClick : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _pauseButtonClick : MutableLiveData<Boolean> = MutableLiveData(false)

    val navigateToNextActivity : LiveData<Any>
        get() = _navigateToNextActivity

    val navigateToPrevActivity : LiveData<Any>
        get() = _navigateToPrevActivity

    val startButtonClick : LiveData<Boolean> = _startButtonClick
    val pauseButtonClick : LiveData<Boolean> = _pauseButtonClick

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
}