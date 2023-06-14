package com.example.foodrecipe.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodrecipe.pojo.MealsItem

@Dao
interface MeaIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertOrUpdate(meal: MealsItem)

   @Delete
   suspend fun delete(meal: MealsItem)

   @Query("SELECT * FROM mealItems")
   fun getAllMeals(): LiveData<List<MealsItem>>

}