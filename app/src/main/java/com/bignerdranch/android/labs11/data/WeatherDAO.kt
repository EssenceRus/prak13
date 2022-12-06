package com.bignerdranch.android.labs11.data

import android.provider.BaseColumns
import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.labs11.Weather
import com.bignerdranch.android.labs11.data.model.CitiesTable
import com.bignerdranch.android.labs11.data.model.WeatherTable

@Dao

interface WeatherDAO {

    @Query("SELECT * FROM $CITIES_TABLE")
    fun getAllCities(): LiveData<List<CitiesTable>>


    @Insert
    fun addCity(citiesTable: CitiesTable)

    @Update
    fun saveCities(citiesTable: CitiesTable)

    @Delete
    fun killCity(citiesTable: CitiesTable)


    @Query("SELECT * FROM $WEATHER_TABLE")
    fun getAllWeather(): LiveData<List<WeatherTable>>

    @Query("SELECT * FROM $WEATHER_TABLE WHERE _id=:id")
    fun getWeath(id: Int): LiveData<List<WeatherTable>>

    @Insert
    fun addWeather(weatherTable: WeatherTable)

    @Update
    fun saveWeather(weatherTable: WeatherTable)

    @Delete
    fun killWeather(weatherTable: WeatherTable)




}