package com.example.foodrecipe.retrofit


import com.example.foodrecipe.pojo.Meal
import com.example.foodrecipe.pojo.categories.Category
import com.example.foodrecipe.pojo.popularmeals.PopularMeals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<Meal>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String): Call<Meal>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") id: String): Call<PopularMeals>

    @GET("categories.php")
    fun getAllCategories(): Call<Category>

}