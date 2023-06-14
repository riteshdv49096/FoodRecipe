package com.example.foodrecipe.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipe.activities.CategoryActivity
import com.example.foodrecipe.activities.MealDetailsActivity
import com.example.foodrecipe.adapters.AllCategoriesAdapter
import com.example.foodrecipe.adapters.PopularMealAdapter
import com.example.foodrecipe.databinding.FragmentHomeBinding
import com.example.foodrecipe.pojo.MealsItem
import com.example.foodrecipe.pojo.categories.CategoriesItem
import com.example.foodrecipe.pojo.popularmeals.PopularMealItems
import com.example.foodrecipe.viewmodel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel

    private lateinit var mPopularMealAdapter: PopularMealAdapter
    private lateinit var mAllCategoriesAdapter: AllCategoriesAdapter

    companion object {
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_TITLE = "MEAL_TITLE"
        const val MEAL_THUMB = "MEAL_THUMB"
        const val MEAL_CATEGORY = "MEAL_CATEGORY"
        const val CATEGORY_ITEM = "CATEGORY_ITEM"
    }

    private lateinit var mealData: MealsItem;
//    private lateinit var popularItem: ArrayList<CategoryItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize the mvvm variable
//        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        mPopularMealAdapter = PopularMealAdapter()
        mAllCategoriesAdapter = AllCategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepare recycler view for popular categories
        prepareRecyclerViewForPopularItem()

        //handle random meal for main card
        homeMvvm.getRandomData()
        observeRandomMealData()
        handleOnPressedEvent()

        //handle meal by category for recyclerview
        homeMvvm.getMealsByCategory()
        observeMealsByCategory()

        //handle allCategory
        homeMvvm.getAllCategories()
        observeAllCategories()
        prepareRecyclerViewForCategoriesItem()
        handleOnCategoryPressedEvent()

    }

    private fun handleOnCategoryPressedEvent() {
        mAllCategoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_ITEM, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerViewForCategoriesItem() {
        binding.recViewCategories.apply {
            adapter = mAllCategoriesAdapter
            layoutManager = GridLayoutManager(activity,3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeAllCategories() {
        homeMvvm.observeAllCategories().observe(viewLifecycleOwner, object: Observer<List<CategoriesItem>>{
            override fun onChanged(t: List<CategoriesItem>?) {
                    mAllCategoriesAdapter.setCategoriesData(t as ArrayList<CategoriesItem>)
            }
        })
    }

    private fun prepareRecyclerViewForPopularItem() {
        binding.recViewOverPopularItem.apply {
            adapter = mPopularMealAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun observeMealsByCategory() {
        homeMvvm.observeMealByCategory()
            .observe(viewLifecycleOwner, object : Observer<List<PopularMealItems>> {
                override fun onChanged(t: List<PopularMealItems>?) {
                    mPopularMealAdapter.setData(t as ArrayList<PopularMealItems>)
                }
            })
    }

    private fun handleOnPressedEvent() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID, mealData.idMeal)
            intent.putExtra(MEAL_TITLE, mealData.strMeal)
            intent.putExtra(MEAL_THUMB, mealData.strMealThumb)
            intent.putExtra(MEAL_CATEGORY, mealData.strCategory)
            startActivity(intent)
        }
    }

    // Function to listen the data changes
    private fun observeRandomMealData() {
        homeMvvm.observeRandomMealLiveData()
            .observe(viewLifecycleOwner, object : Observer<MealsItem> {
                override fun onChanged(t: MealsItem?) {
                    Glide.with(this@HomeFragment)
                        .load(t?.strMealThumb)
                        .into(binding.imgRandomMeal)

                    mealData = t!!
                }
            })
    }
}