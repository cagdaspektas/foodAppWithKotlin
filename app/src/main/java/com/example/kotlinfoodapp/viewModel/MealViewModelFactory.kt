package com.example.kotlinfoodapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinfoodapp.dao.MealDataBase

class MealViewModelFactory( val mealDataBase: MealDataBase):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDataBase) as T
    }
}