package com.bignerdranch.android.labs11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.WeatherDAO
import com.bignerdranch.android.labs11.data.WeatherDatabase
import com.bignerdranch.android.labs11.data.model.CitiesTable
import com.bignerdranch.android.labs11.data.model.WeatherTable
import java.util.concurrent.Executors


class CitiesAddActivity : AppCompatActivity() {
    private lateinit var btnInput: Button
    private lateinit var addCity: EditText
    private lateinit var db: WeatherDatabase
    private lateinit var weatherDAO: WeatherDAO
    private var citiesTable: MutableList<CitiesTable> = mutableListOf()
    private var cities: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_add)
        btnInput = findViewById(R.id.button5)
        addCity = findViewById(R.id.editTextTextPersonName4)
        db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
        weatherDAO = db.weatherDAO()

        val executor = Executors.newSingleThreadExecutor()

        val citiesType = weatherDAO.getAllCities()
        citiesType.observe(this){
            citiesTable.addAll(it)
            it.forEach{
                cities.add(it.city)

            }
        }

        btnInput.setOnClickListener{
            if(addCity.text.isNotEmpty()) {


                executor.execute {
                    weatherDAO.addCity(CitiesTable(0,addCity.text.toString()))
                }
                val citiesType = weatherDAO.getAllCities()
                citiesType.observe(this) {
                    cities.clear()
                    it.forEach {
                        cities.add(it.city)
                    }
                }
                addCity.setText("")
            }
        }

    }
}