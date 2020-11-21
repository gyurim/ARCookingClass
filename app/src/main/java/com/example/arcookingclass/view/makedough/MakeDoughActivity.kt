package com.example.arcookingclass.view.makedough

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.databinding.ActivityMakeDoughBinding
import com.example.arcookingclass.view.chop.ChopActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity

class MakeDoughActivity : AppCompatActivity() {
    private lateinit var viewModel : MakeDoughViewModel
    private lateinit var binding : ActivityMakeDoughBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_dough)
        viewModel = ViewModelProvider(this).get(MakeDoughViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                Intent(
                    this,
                    TurnOnGasActivity::class.java
                )
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })
    }
}