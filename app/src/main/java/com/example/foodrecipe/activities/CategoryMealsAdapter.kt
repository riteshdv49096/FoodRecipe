package com.example.foodrecipe.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.CategoryItemBinding
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems
import com.example.foodrecipe.viewmodel.CategoryMealViewModel

class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealViewHolder>() {

    var mCategoryMeals = ArrayList<PopularMealItems>()

    class CategoryMealViewHolder(var binding: CategoryItemBinding): ViewHolder(binding.root)

    fun setCategoryMeal(meal: ArrayList<PopularMealItems>){
        mCategoryMeals = meal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mCategoryMeals[position].strMealThumb)
            .into(holder.binding.imgCategoryItem)
        holder.binding.tvCategoryName.text = mCategoryMeals[position].strMeal
    }

    override fun getItemCount(): Int {
     return mCategoryMeals.size
    }

}