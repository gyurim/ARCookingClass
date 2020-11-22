package com.example.arcookingclass.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "image_url") var image_url: Int?,
    @ColumnInfo(name = "ingredient") var ingredient: String? = "",
    @ColumnInfo(name = "level") var level : String? = "",
    @ColumnInfo(name = "time") var time :  String? = "",
    @ColumnInfo(name = "islearn") var islearn : Boolean = false
) : Serializable