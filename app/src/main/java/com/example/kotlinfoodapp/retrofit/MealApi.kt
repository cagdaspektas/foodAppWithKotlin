package com.example.kotlinfoodapp.retrofit

import com.example.kotlinfoodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}