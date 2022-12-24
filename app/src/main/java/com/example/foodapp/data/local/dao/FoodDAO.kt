package com.example.foodapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.model.FoodModel

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(foodModel: FoodModel)

    @Query("SELECT * FROM food_db")
    suspend fun getFood(): MutableList<FoodModel>
}