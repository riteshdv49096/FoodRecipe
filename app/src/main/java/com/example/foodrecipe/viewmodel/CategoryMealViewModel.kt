package com.example.foodrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.pojo.categories.CategoriesItem
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems
import com.example.foodrecipe.pojo.popularmeals.PopularMeals
import com.example.foodrecipe.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel: ViewModel() {

    private var allCategoryLiveData = MutableLiveData<List<PopularMealItems>>()

    fun getAllMealByCategoryName(name: String){
        RetrofitClient.api.getMealsByCategory(name)
            .enqueue(object: Callback<PopularMeals>{
                override fun onResponse(
                    call: Call<PopularMeals>,
                    response: Response<PopularMeals>
                ) {
                    if (response.isSuccessful){
                        allCategoryLiveData.value = response.body()?.meals
                    }
                }
                override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
                    Log.d("CategoryMealViewModel", "onFailure: "+t.message.toString())
                }

            })
    }

    fun observeAllCategoryMealByName(): LiveData<List<PopularMealItems>>{
        return allCategoryLiveData
    }

}