package com.example.foodrecipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.pojo.Meal
import com.example.foodrecipe.pojo.MealsItem
import com.example.foodrecipe.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel(var mealDatabase: MealDatabase): ViewModel() {
    private val mealDetailLiveData = MutableLiveData<MealsItem>();

    fun getMealDetails(id: String){
        RetrofitClient.api.getMealDetails(id)
            .enqueue(object: Callback<Meal>{
                override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                    if (response.body()!= null){
                        mealDetailLiveData.value = response.body()!!.meals[0]
                    }else{
                        return
                    }
                }

                override fun onFailure(call: Call<Meal>, t: Throwable) {
                        Log.d("MealDetails", t.message.toString())
                }
            })
    }

    fun observeMealDetails(): LiveData<MealsItem>{
        return  mealDetailLiveData;
    }

    fun insertMealInDB(meal: MealsItem){
        viewModelScope.launch {
            mealDatabase.mealDAO().insertOrUpdate(meal)
        }
    }

    fun deleteMealFromDB(meal: MealsItem){
        viewModelScope.launch {
            mealDatabase.mealDAO().delete(meal)
        }
    }
}