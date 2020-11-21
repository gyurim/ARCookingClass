package com.example.arcookingclass.view.completecooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.databinding.ActivityCompleteCookingBinding
import com.example.arcookingclass.view.main.MainActivity

class CompleteCookingActivity : AppCompatActivity(){
    private lateinit var viewModel: CompleteCookingViewModel
    private lateinit var binding: ActivityCompleteCookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_cooking)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_cooking)
        viewModel = ViewModelProvider(this).get(CompleteCookingViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })

    }
}