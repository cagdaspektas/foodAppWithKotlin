package com.example.kotlinfoodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.kotlinfoodapp.activities.MealActivity

import com.example.kotlinfoodapp.databinding.FragmentHomeBinding
import com.example.kotlinfoodapp.pojo.Meal

import com.example.kotlinfoodapp.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private  lateinit var binding:FragmentHomeBinding
    private  lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal

    //TODO    companion object kullandık çünkü Kotlin’de Static Properties & Static Function oluşturmamıza izin vermiyor. Static properties oluşturabilmemiz için Companion Object kullanmamız gerekiyor.
    companion object{
        const val MEAL_ID="com.example.kotlinfoodapp.fragments.idMeal"
        const val MEAL_NAME="com.example.kotlinfoodapp.fragments.nameMeal"
        const val MEAL_THUMB="com.example.kotlinfoodapp.fragments.thumbMeal"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm= ViewModelProvider(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClicked()

    }

    private fun onRandomMealClicked() {
        binding.imgRandomMealCard.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
    homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,{meal->
        Glide.with(this).load(meal.strMealThumb).into(binding.imgRandomMeal)
        this.randomMeal=meal
    })
    }
}