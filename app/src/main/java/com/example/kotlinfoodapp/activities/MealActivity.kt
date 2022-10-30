package com.example.kotlinfoodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlinfoodapp.R
import com.example.kotlinfoodapp.databinding.ActivityMealBinding
import com.example.kotlinfoodapp.fragments.HomeFragment
import com.example.kotlinfoodapp.pojo.Meal
import com.example.kotlinfoodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private  lateinit var mealViewModel: MealViewModel
    private  lateinit var youtubeLink: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealViewModel=ViewModelProvider(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationViews()
        loadingCase()
        mealViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealViewModel.observeMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()

                val meal=t
                binding.tvCategory.text= "Category:${meal!!.strCategory}"
                binding.tvArea.text="Area:${meal!!.strArea}"
                binding.tvInstructorsSteps.text=meal.strInstructions
                youtubeLink=meal.strYoutube

            }
        })
    }

    private fun setInformationViews() {
        //TODO applicationContext çünkü aldığımız mealThumb dışarıdan companion object olarak alınıyor
        Glide.with(applicationContext).load(mealThumb).into(binding.imageMealDetail)
        binding.collapsinToolBar.title=mealName
        binding.collapsinToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsinToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent=intent
       mealId= intent.getStringExtra(HomeFragment.MEAL_ID)!!
    mealName=    intent.getStringExtra(HomeFragment.MEAL_NAME)!!
      mealThumb=  intent.getStringExtra(HomeFragment.MEAL_THUMB)!!



    }
    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnAddToFav.visibility=View.INVISIBLE
        binding.tvInstructors.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnAddToFav.visibility=View.VISIBLE
        binding.tvInstructors.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE
    }
}