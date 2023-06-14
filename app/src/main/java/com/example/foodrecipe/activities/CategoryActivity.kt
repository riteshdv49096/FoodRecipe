package com.example.foodrecipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.ActivityCategoryBinding
import com.example.foodrecipe.fragments.HomeFragment
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems
import com.example.foodrecipe.retrofit.MealAPI
import com.example.foodrecipe.retrofit.RetrofitClient
import com.example.foodrecipe.viewmodel.CategoryMealViewModel

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    private lateinit var categoryMealViewModel: CategoryMealViewModel

    private lateinit var mCategoryMealAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealViewModel = ViewModelProviders.of(this)[CategoryMealViewModel::class.java]
        val name = intent.getStringExtra(HomeFragment.CATEGORY_ITEM)
        binding.tvCategoryCount.text = name
        mCategoryMealAdapter = CategoryMealsAdapter()

        if (name != null) {
            getAllMealsByCategoryFromAPI(name)
            observeMealsByCategory()
        }
        prepareAdapter()
    }

    private fun prepareAdapter() {
        binding.rcvCategory.apply {
            adapter = mCategoryMealAdapter
            layoutManager = GridLayoutManager(applicationContext, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeMealsByCategory() {
        categoryMealViewModel.observeAllCategoryMealByName().observe(this, { mealList->
            mCategoryMealAdapter.setCategoryMeal(mealList as ArrayList<PopularMealItems>)
            binding.tvCategoryCount.text = mealList.size.toString()
        })
    }

    private fun getAllMealsByCategoryFromAPI(name: String) {
        categoryMealViewModel.getAllMealByCategoryName(name)
    }
}