package com.example.foodrecipe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.ActivityMealDetailsBinding
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.fragments.HomeFragment
import com.example.foodrecipe.pojo.MealsItem
import com.example.foodrecipe.viewmodel.MealDetailsViewModel
import com.example.foodrecipe.viewmodel.ViewModelMealFactory

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var mMealTitle: String
    private lateinit var mMealThumb: String
    private lateinit var mMealId: String
    private lateinit var mMealCategory: String

    private lateinit var binding: ActivityMealDetailsBinding
    private lateinit var mealDetailsMvvm: MealDetailsViewModel
    private lateinit var mealDetails: MealsItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE

        var mealDatabase = MealDatabase.getMealDatabase(this);
        var viewModelFactory = ViewModelMealFactory(mealDatabase)


        mealDetailsMvvm = ViewModelProvider(this, viewModelFactory)[MealDetailsViewModel::class.java]
        getIntentsFromHome()
        showDataToView()
        callAPIToGetDetails()
        handleOnClick()
        handleOnFavoriteClick()
    }

    private fun handleOnFavoriteClick() {
        binding.fab.setOnClickListener {
            mealDetails?.let {
                mealDetailsMvvm.insertMealInDB(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleOnClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealDetails.strYoutube))
            startActivity(intent);
        }
    }

    private fun callAPIToGetDetails() {
        mealDetailsMvvm.getMealDetails(mMealId)
        listenToMealDetails()
    }

//    private lateinit var mealToSave: MealsItem
    private fun listenToMealDetails() {

        mealDetailsMvvm.observeMealDetails().observe(this, object : Observer<MealsItem> {
            override fun onChanged(t: MealsItem?) {
                mealDetails = t!!
//                mealToSave = mealDetails
                binding.tvArea.text = t.strArea
                binding.tvInstructionDetails.text = t.strInstructions
            }
        })
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showDataToView() {
        Glide.with(this)
            .load(mMealThumb)
            .into(binding.imgToolbar)
        binding.collapsingToolBar.title = mMealTitle
        binding.tvCategory.text = "Category: ${mMealCategory}"
    }

    private fun getIntentsFromHome() {
        mMealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mMealTitle = intent.getStringExtra(HomeFragment.MEAL_TITLE)!!
        mMealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
        mMealCategory = intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!
    }
}