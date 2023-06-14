package com.example.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.PopularMealItemBinding
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems

class PopularMealAdapter: RecyclerView.Adapter<PopularMealAdapter.PopularItemViewHolder>() {

    var mPopularMeal = ArrayList<PopularMealItems>()

    class PopularItemViewHolder(var binding: PopularMealItemBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(mealList: ArrayList<PopularMealItems>){
        this.mPopularMeal = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(PopularMealItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mPopularMeal[position].strMealThumb)
            .into(holder.binding.imgPopularItem)
    }

    override fun getItemCount(): Int {
       return mPopularMeal.size
    }
}