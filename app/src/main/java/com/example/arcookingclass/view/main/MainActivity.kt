package com.example.arcookingclass.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ActivityMainBinding
import com.example.arcookingclass.view.ingredient.IngredientActivity
import com.example.arcookingclass.view.recipeintro.RecipeIntroActivity
import java.lang.Exception

class MainActivity : AppCompatActivity(){
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        try {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        } catch (e : Exception){
            e.printStackTrace()
        }

        mainAdapter = MainAdapter(onItemClickListener())
        binding.lifecycleOwner = this
        binding.recipeRecyclerView.adapter = mainAdapter
        binding.viewModel = viewModel
        observeLiveData()

    }

    private fun onItemClickListener(): MainAdapter.OnItemClickListener {
        return object : MainAdapter.OnItemClickListener{
            override fun onItemClick(recipe: Recipe) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        RecipeIntroActivity::class.java
                    ).putExtra(RecipeIntroActivity.EXTRA_RECIPE_DATA, recipe)
                )
            }
        }
    }

    private fun observeLiveData(){
        viewModel.recipeList.observe(this, Observer {
            mainAdapter.submitList(it)
        })
    }
}