package com.example.arcookingclass.view.recipeintro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ActivityRecipeIntroBinding
import com.example.arcookingclass.view.chop.ChopActivity
import com.example.arcookingclass.view.ingredient.IngredientActivity

class RecipeIntroActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeIntroViewModel
    private lateinit var binding: ActivityRecipeIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_intro)
        viewModel = ViewModelProvider(this).get(RecipeIntroViewModel::class.java)
        binding.recipeItem = intent.getSerializableExtra(IngredientActivity.EXTRA_RECIPE_DATA) as Recipe
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.navigateToActivity.observe(this, Observer {
            startActivity(
                Intent(
                    this@RecipeIntroActivity,
                    IngredientActivity::class.java
                ).putExtra(IngredientActivity.EXTRA_RECIPE_DATA, binding.recipeItem)
            )
        })
    }

    companion object {
        const val EXTRA_RECIPE_DATA = "recipe_data"
    }
}