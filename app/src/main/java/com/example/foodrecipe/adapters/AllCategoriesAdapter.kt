package com.example.foodrecipe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.CategoryItemBinding
import com.example.foodrecipe.databinding.PopularMealItemBinding
import com.example.foodrecipe.pojo.categories.CategoriesItem
import java.util.zip.Inflater

class AllCategoriesAdapter: RecyclerView.Adapter<AllCategoriesAdapter.CategoriesViewHolder>() {

    var categoriesList = ArrayList<CategoriesItem>()
    var onItemClick: ((CategoriesItem) -> Unit)?= null

    class CategoriesViewHolder(var binding: CategoryItemBinding):ViewHolder(binding.root)

    fun setCategoriesData(data: List<CategoriesItem>){
        categoriesList = data as ArrayList<CategoriesItem>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        Log.d("AllCategoriesAdapter", "onCreateViewHolder: ")
      return  CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Log.d("AllCategoriesAdapter", "onBindViewHolder: ")
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategoryItem)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
                onItemClick?.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
     return  categoriesList.size
    }

}