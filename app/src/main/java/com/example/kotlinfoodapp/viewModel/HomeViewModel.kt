package com.example.kotlinfoodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfoodapp.pojo.Meal
import com.example.kotlinfoodapp.pojo.MealList
import com.example.kotlinfoodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
private   var randomMealLiveData= MutableLiveData<Meal>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val randomMeal: Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                    Log.d("RandomMeal", "mealId:${randomMeal.idMeal} name ${randomMeal.strMeal} ")
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("RandomMealFail", "${t.message.toString()}")
            }
        })
    }
//TODO live data değişemezken muteabe live data değişebilir burdaki fun amacı private olan MutableLiveDatayı dışarıdan okunmayacak hale getirip sadece bu class içinde değiştirmek
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

}