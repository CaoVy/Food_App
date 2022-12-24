package com.example.foodapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodapp.data.local.dao.FoodDAO
import com.example.foodapp.model.FoodModel

@Database(entities = [FoodModel::class], version = 1)
abstract class FoodDatabase(): RoomDatabase() {

    abstract fun foodDAO(): FoodDAO

    companion object {
        private var INSTANCE: FoodDatabase? = null
        fun getInstance(context: Context): FoodDatabase? {
            if (INSTANCE == null) {
                synchronized(FoodDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FoodDatabase::class.java, "food_db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}