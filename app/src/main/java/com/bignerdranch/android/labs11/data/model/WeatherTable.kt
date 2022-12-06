package com.bignerdranch.android.labs11.data.model

import android.provider.BaseColumns
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.labs11.data.WEATHER_TABLE
import java.util.*

@Entity(tableName = WEATHER_TABLE)
class WeatherTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    @NonNull
    val id:Int,
    var citiesId: Int,

    var Daytime_Temperature: String,
    var NightTemp: String
)

