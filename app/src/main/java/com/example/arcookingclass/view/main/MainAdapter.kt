package com.example.arcookingclass.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ItemRecipeBinding

class MainAdapter(private val itemClickListener: OnItemClickListener) :
    PagedListAdapter<Recipe, MainViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.itemView.setOnClickListener {
            item?.let { itemClickListener.onItemClick(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>(){
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.name == newItem.name && newItem.image_url == oldItem.image_url

        }
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }
}