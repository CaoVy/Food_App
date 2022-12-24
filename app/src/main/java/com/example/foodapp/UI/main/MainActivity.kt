package com.example.foodapp.UI.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.R
import com.example.foodapp.UI.adapter.FoodAdapter
import com.example.foodapp.model.FoodModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var foodModels: MutableList<FoodModel>
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapter()
        registerLiveData()
        btnSave.setOnClickListener {
            saveFood()
        }
        mainViewModel.getFood()
    }

    private fun setupAdapter() {
        foodAdapter = FoodAdapter()
        rvFood.adapter = foodAdapter
        val openFileJson = applicationContext.assets.open("food.json").bufferedReader().use {
            it.readText()
        }
        val mutableListTutorialType = object : TypeToken<MutableList<FoodModel>>() {}.type
        foodModels = Gson().fromJson(openFileJson, mutableListTutorialType)
        foodAdapter.apply {
            updateAdapter(foodModels)
            itemCheck = {
                if (foodModels[it].isSelect == false) {
                    foodModels[it].isSelect = true
                    printDifferenceDateForHours(foodModels[it])
                } else {
                    foodModels[it].isSelect = false
                    countDownTimer?.cancel()
                }
                notifyDataSetChanged()
            }
            itemRemove = {
                if ((foodModels[it].total ?: 0) > 1) {
                    foodModels[it].total = foodModels[it].total?.minus(1)
                }
                notifyDataSetChanged()
            }

            itemAdd = {
                foodModels[it].total = foodModels[it].total?.plus(1)
                notifyDataSetChanged()
            }
        }
    }

    private fun registerLiveData() {
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(this.application)
        )[MainViewModel::class.java]
        mainViewModel.foodModels.observe(this) {
            it.forEach { food ->
                val index = foodModels.indexOfFirst { foodModel -> foodModel.name == food.name }
                if (foodModels[index].name == food.name) {
                    foodModels[index].isSelect = food.isSelect
                    foodModels[index].total = food.total
                }
            }
            foodAdapter.notifyDataSetChanged()
        }
    }

    private fun saveFood() {
        foodModels.filter { food -> food.isSelect == true }.forEach {
            mainViewModel.addFood(it)
        }
    }

    private fun printDifferenceDateForHours(foodModel: FoodModel) {
        val currentTime = Calendar.getInstance().time
        val endDateDay = foodModel.expiry
        val format1 = SimpleDateFormat("hh:mm:ss, dd", Locale.getDefault())
        val endDate = format1.parse(endDateDay)

        //milliseconds
        val different = currentTime.time + endDate.time
                countDownTimer = object : CountDownTimer(different, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli
                Toast.makeText(
                    application,
                    "${elapsedHours}h, ${elapsedMinutes}m, ${elapsedSeconds}s, ${elapsedDays}dd",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFinish() {
                Toast.makeText(application, "Sản phẩm đã hết hạn sử dụng", Toast.LENGTH_SHORT)
                    .show()
                countDownTimer?.cancel()
            }
        }.start()
    }
}
