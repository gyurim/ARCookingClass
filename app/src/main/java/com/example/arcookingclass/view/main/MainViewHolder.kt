package com.example.arcookingclass.view.main

import androidx.recyclerview.widget.RecyclerView
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ItemRecipeBinding

class MainViewHolder(private val binding : ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(recipe : Recipe?){
        with(binding){
            recipeItem = recipe
        }
    }

}