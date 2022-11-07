package com.example.kotlinfoodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodapp.R
import com.example.kotlinfoodapp.adapter.CategoryMealsAdapter
import com.example.kotlinfoodapp.databinding.ActivityCategoryMealsBinding
import com.example.kotlinfoodapp.fragments.HomeFragment
import com.example.kotlinfoodapp.viewModel.CategoryMealsViewModel
import com.example.kotlinfoodapp.viewModel.HomeViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecycleView()

        categoryMealsViewModel=ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {
            mealsList->categoryMealsAdapter.setMealList(mealsList)
            binding.tvCategoryCount.text=mealsList.size.toString()
        })

    }

    private fun prepareRecycleView() {
        categoryMealsAdapter=CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}