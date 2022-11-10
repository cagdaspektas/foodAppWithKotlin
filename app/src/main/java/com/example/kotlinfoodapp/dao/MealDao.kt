package com.example.kotlinfoodapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlinfoodapp.pojo.Meal

@Dao
interface MealDao {

    //TODO suspend async gibi flutterdaki beklemeye yarıyor
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(meal:Meal)

  /*
  update bu yönetimide kullanılabilir daha kolayı insert içine eğer zaten varsa o datayı eklemektir o işlemde  @Insert(onConflict = OnConflictStrategy.REPLACE) bu şekilde yapılır
  @Update
   suspend fun updateMeal(meal:Meal)*/

   @Delete
  fun delete(meal: Meal)

   @Query("Select * from mealInformation")
   fun getMeals():LiveData<List<Meal>>
}