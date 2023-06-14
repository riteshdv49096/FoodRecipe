package com.example.foodrecipe.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverters {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String{
        if(attribute == null){
            return ""
        }
        return attribute.toString()
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any{
        if(attribute == null){
            return ""
        }
        return attribute
    }
}