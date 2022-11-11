package com.example.kotlinfoodapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodapp.R
import com.example.kotlinfoodapp.activities.MainActivity
import com.example.kotlinfoodapp.adapter.CategoriesAdapter
import com.example.kotlinfoodapp.databinding.FragmentFavoritesBinding
import com.example.kotlinfoodapp.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
private lateinit var categoriesAdapter: CategoriesAdapter
private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    prepareRecycleView()
    observeCategories()

    }

    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer {
            categories->categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecycleView() {
        categoriesAdapter= CategoriesAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

}