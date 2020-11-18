package com.example.arcookingclass.view.ingredient

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ActivityIngredientBinding
import com.example.arcookingclass.view.step.Step1Activity

class IngredientActivity : AppCompatActivity() {
    private lateinit var viewModel: IngredientViewModel
    private lateinit var binding : ActivityIngredientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ingredient)
        // layout 태그 필요 -> Activity()Binding 생성
        viewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)
        binding.recipeItem = intent.getSerializableExtra(EXTRA_RECIPE_DATA) as Recipe
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.navigateToActivity.observe(this, Observer {
            startActivity(
                Intent(
                    this@IngredientActivity,
                    Step1Activity::class.java
                )
            )
        })
    }

    companion object {
        const val EXTRA_RECIPE_DATA = "recipe_data"
    }
}