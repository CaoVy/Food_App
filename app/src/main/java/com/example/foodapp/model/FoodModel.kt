package com.example.foodapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class FoodDataModel(
    var foodModels: MutableList<FoodModel>? = mutableListOf()
)


@Entity(tableName = "food_db")

@Parcelize
data class FoodModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @Expose
    @SerializedName("calories")
    val calories: Int? = 0,
    @Expose
    @SerializedName("expiry")
    val expiry: String? = "",
    @Expose
    @SerializedName("name")
    val name: String? = "",
    @Expose
    @SerializedName("quantity")
    val quantity: String? = "",
    var isSelect: Boolean? = false,
    var total: Int? = 1
): Parcelable