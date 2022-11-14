package com.example.kotlinfoodapp.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.kotlinfoodapp.R
import com.example.kotlinfoodapp.activities.MainActivity
import com.example.kotlinfoodapp.activities.MealActivity
import com.example.kotlinfoodapp.databinding.FragmentBottomSheetBinding
import com.example.kotlinfoodapp.databinding.FragmentFavoritesBinding
import com.example.kotlinfoodapp.fragments.HomeFragment
import com.example.kotlinfoodapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { viewModel.getMealById(it) }

        observeBottomSheetMeal()

        onBottomSheetLongClick()
    }

    private fun onBottomSheetLongClick() {
        binding.bottomSheet.setOnClickListener {
    if (mealName !=null && mealThumb !=null){
        val intent=Intent(activity,MealActivity::class.java)

        intent.apply {
            putExtra(HomeFragment.MEAL_ID,mealId)
            putExtra(HomeFragment.MEAL_NAME,mealName)
            putExtra(HomeFragment.MEAL_THUMB,mealThumb)


        }
        startActivity(intent)
    }
        }
    }
    private var mealName :String?=null
    private var mealThumb :String?=null


    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealsLiveData().observe(viewLifecycleOwner, Observer {

            Glide.with(this).load(it.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text=it.strArea
            binding.tvBottomSheetCategories.text=it.strCategory
            binding.bottomSheetMealName.text=it.strMeal

            mealName=it.strMeal
            mealThumb=it.strMealThumb
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}