package com.example.foodrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.pojo.Meal
import com.example.foodrecipe.pojo.MealsItem
import com.example.foodrecipe.pojo.categories.CategoriesItem
import com.example.foodrecipe.pojo.categories.Category
import com.example.foodrecipe.pojo.popularmeals.PopularMeals
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems
import com.example.foodrecipe.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This is view model class for bind the api data to the UI
 */
class HomeViewModel : ViewModel() {

    // variable for live data of meal items
    private val randomMealLiveData = MutableLiveData<MealsItem>()

    private val mealByCategoryLiveData = MutableLiveData<List<PopularMealItems>>()

    private val  allCategoriesLiveData = MutableLiveData<List<CategoriesItem>>()

    /**
     * function to get the random meals data from API
     */
    fun getRandomData() {
        RetrofitClient.api.getRandomMeal().enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.body() != null) {
                    val mealData: MealsItem? = response.body()!!.meals[0]
                    randomMealLiveData.value = mealData!!
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    /**
     * This function is user to observe the live data from the API
     * Listen to the data
     * (Read Only)
     */
    fun observeRandomMealLiveData(): LiveData<MealsItem>{
        return  randomMealLiveData
    }

    fun getMealsByCategory(){
        RetrofitClient.api.getMealsByCategory("Seafood")
            .enqueue(object : Callback<PopularMeals>{
                override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
                    if (response.body() !== null){
                        val res = response.body()!!.meals
                        mealByCategoryLiveData.value = res
                    }
                }

                override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
                    Log.d("TAG", "onFailure: "+t.message.toString())
                }
            })
    }

    fun observeMealByCategory(): LiveData<List<PopularMealItems>> {
        return mealByCategoryLiveData
    }


    fun getAllCategories(){
        RetrofitClient.api.getAllCategories()
            .enqueue(object: Callback<Category>{
                override fun onResponse(
                    call: Call<Category>,
                    response: Response<Category>
                ) {
                    if (response.isSuccessful){
                        allCategoriesLiveData.value = response.body()?.categories
                    }
                }
                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Log.d("", "onFailure: "+t.message.toString())
                }
            })
    }

    fun observeAllCategories(): LiveData<List<CategoriesItem>>{
        return allCategoriesLiveData;
    }

}