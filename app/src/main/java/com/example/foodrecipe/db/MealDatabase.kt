package com.example.foodrecipe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipe.pojo.MealsItem


@Database(entities = [MealsItem::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverters::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDAO(): MeaIDao

    companion object {
        var mealDatabase: MealDatabase? = null

        @Synchronized
        fun getMealDatabase(context: Context): MealDatabase {
            if (mealDatabase == null) {
                mealDatabase = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return mealDatabase as MealDatabase
        }
    }
}