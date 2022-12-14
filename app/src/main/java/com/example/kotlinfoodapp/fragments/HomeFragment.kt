package com.example.kotlinfoodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlinfoodapp.activities.CategoryMealsActivity
import com.example.kotlinfoodapp.activities.MainActivity
import com.example.kotlinfoodapp.activities.MealActivity
import com.example.kotlinfoodapp.adapter.CategoriesAdapter
import com.example.kotlinfoodapp.adapter.MostPopularAdapter

import com.example.kotlinfoodapp.databinding.FragmentHomeBinding
import com.example.kotlinfoodapp.fragments.bottomSheet.BottomSheetFragment
import com.example.kotlinfoodapp.pojo.MealsByCategory
import com.example.kotlinfoodapp.pojo.Meal

import com.example.kotlinfoodapp.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private  lateinit var binding:FragmentHomeBinding
    private  lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private  lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    //TODO    companion object kullandık çünkü Kotlin’de Static Properties & Static Function oluşturmamıza izin vermiyor. Static properties oluşturabilmemiz için Companion Object kullanmamız gerekiyor.
    companion object{
        const val MEAL_ID="com.example.kotlinfoodapp.fragments.idMeal"
        const val MEAL_NAME="com.example.kotlinfoodapp.fragments.nameMeal"
        const val MEAL_THUMB="com.example.kotlinfoodapp.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.kotlinfoodapp.fragments.categoryName"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      viewModel=(activity as MainActivity).viewModel
     //    viewModel= ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter= MostPopularAdapter()

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
        preparePopularItemRecyclerView()
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClicked()
        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

        onPopularItemLongClick()


    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongClick={
            val mealBottomSheetFragment= BottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter=CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer {
            categories->categoriesAdapter.setCategoryList(categories) })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick={meal->
            val  intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)


        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
           adapter= popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) {mealList->popularItemsAdapter.setMeals(mealList as ArrayList<MealsByCategory>)   }
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
    viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,{ meal->
        Glide.with(this).load(meal.strMealThumb).into(binding.imgRandomMeal)
        this.randomMeal=meal
    })
    }
}