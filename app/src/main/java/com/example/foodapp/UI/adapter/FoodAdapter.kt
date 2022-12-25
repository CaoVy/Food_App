package com.example.foodapp.UI.adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodapp.R
import com.example.foodapp.model.FoodModel
import kotlinx.android.synthetic.main.items_food.view.*
import java.text.SimpleDateFormat
import java.util.*

class FoodAdapter: Adapter<FoodAdapter.FoodViewHolder>() {

    private var foodModels = mutableListOf<FoodModel>()
    var itemCheck: ((Int) -> Unit)? = null
    var itemRemove: ((Int) -> Unit)? = null
    var itemAdd: ((Int) -> Unit)? = null

    fun updateAdapter(foodModels: MutableList<FoodModel>) {
        this.foodModels.clear()
        this.foodModels.addAll(foodModels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_food, parent, false)
        return  FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindHolder(foodModels[position])
        holder.itemView.setOnClickListener {
            itemCheck?.invoke(position)
        }
        holder.itemView.imgAdd.setOnClickListener {
            itemAdd?.invoke(position)
        }
        holder.itemView.imgRemove.setOnClickListener {
            itemRemove?.invoke(position)
        }
    }

    override fun getItemCount() = foodModels.size

    inner class FoodViewHolder(itemView: View): ViewHolder(itemView) {
        fun bindHolder(foodModel: FoodModel) {
            foodModel.apply {
                if (isSelect == true) {
                    itemView.imgCheckBox.setImageResource(R.drawable.ic_check_box)
                    itemView.viewAction.visibility = View.VISIBLE
                } else {
                    itemView.imgCheckBox.setImageResource(R.drawable.ic_un_check)
                    itemView.viewAction.visibility = View.GONE
                }

                itemView.tvNameFood.text = name
                itemView.tvQuantitative.text = quantity
                itemView.tvCalo.text = calories.toString()
                itemView.viewAction.tvTotal.text = total.toString()
            }
        }
    }
}