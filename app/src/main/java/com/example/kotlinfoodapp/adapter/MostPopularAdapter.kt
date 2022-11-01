package com.example.kotlinfoodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinfoodapp.databinding.PopularItemsBinding
import com.example.kotlinfoodapp.pojo.CategoryMeals

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((CategoryMeals)->Unit)
    private var mealList=ArrayList<CategoryMeals>()

    fun  setMeals(mealList:ArrayList<CategoryMeals>){
        this.mealList=mealList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
      return  PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
  Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
      return  mealList.size
    }

    class PopularMealViewHolder(var binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}