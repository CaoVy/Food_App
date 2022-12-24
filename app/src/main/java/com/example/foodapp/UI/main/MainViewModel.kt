package com.example.foodapp.UI.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.source.FoodRepository
import com.example.foodapp.model.FoodModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    private val foodRepository = FoodRepository.getInstance(application)

    val foodModels = MutableLiveData<MutableList<FoodModel>>()

    fun addFood(foodModel: FoodModel) {
        viewModelScope.launch {
            try {
                foodRepository.addFood(foodModel)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFood() {
        viewModelScope.launch {
            try {
                val data = foodRepository.getFood()
                foodModels.value = data ?: mutableListOf()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}