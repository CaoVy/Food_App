package com.example.foodapp.data.source

import android.app.Application
import com.example.foodapp.data.local.database.FoodDatabase
import com.example.foodapp.model.FoodModel

class FoodRepository(application: Application) {

    private val foodDatabase = FoodDatabase.getInstance(application)

    suspend fun addFood(foodModel: FoodModel) {
        foodDatabase?.foodDAO()?.addFood(foodModel)
    }

    suspend fun getFood() = foodDatabase?.foodDAO()?.getFood()

    companion object {
        private var instance: FoodRepository? = null
        fun getInstance(application: Application) = synchronized(this) {
            instance ?: FoodRepository(application).also { it }
        }
    }
}