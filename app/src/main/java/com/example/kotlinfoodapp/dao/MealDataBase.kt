package com.example.kotlinfoodapp.dao

import android.content.Context
import androidx.room.*
import com.example.kotlinfoodapp.pojo.Meal


@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase:RoomDatabase() {
    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        var INSTANCE:MealDataBase?=null


        @Synchronized
        fun getInstance(context: Context):MealDataBase{
            if (INSTANCE==null){
                INSTANCE=Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "meal.db"

                ).allowMainThreadQueries().build()
            }
            return INSTANCE as MealDataBase

        }
    }
}