package com.bignerdranch.android.labs11.data.model

import android.provider.BaseColumns
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.labs11.data.CITIES_TABLE

@Entity(tableName = CITIES_TABLE)
data class CitiesTable(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id:Int,
    @ColumnInfo(index = true)
    var city:String
)



