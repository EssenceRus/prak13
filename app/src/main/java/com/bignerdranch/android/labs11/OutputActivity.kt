package com.bignerdranch.android.labs11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.WeatherDAO
import com.bignerdranch.android.labs11.data.WeatherDatabase
import com.bignerdranch.android.labs11.data.model.CitiesTable
import com.bignerdranch.android.labs11.data.model.WeatherTable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.Executors

class OutputActivity : AppCompatActivity() {
    private val Weath : MutableList<WeatherTable> = mutableListOf()
    private val Cities : MutableList<CitiesTable> = mutableListOf()

    private var index: Int = -1
    private lateinit var db: WeatherDatabase
    private lateinit var weatherDAO: WeatherDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)

        db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
        weatherDAO = db.weatherDAO()
        DataBasetoList()
        updateInfo()





    }
    fun DataBasetoList(){
        Weath.clear()
        Cities.clear()
        val Weathers = weatherDAO.getAllWeather()
        val CitiesT = weatherDAO.getAllCities()
        CitiesT.observe(this, androidx.lifecycle.Observer {
            it.forEach {
                Cities.add(CitiesTable(it.id,it.city))

            }
            updateInfo()
        })
        Weathers.observe(this, androidx.lifecycle.Observer {
            it.forEach {
                Weath.add(WeatherTable(it.id,it.citiesId,it.Daytime_Temperature,it.NightTemp))

            }
            updateInfo()
        })
    }
    override fun onResume() {
        super.onResume()
        if(index != -1)
        {
            DataBasetoList()
            updateInfo()
        }

    }
    fun updateInfo(){
        val rv = findViewById<RecyclerView>(R.id.recylerView)
        val adapter = WeatherRVAdapter(this, Weath,Cities)
        val rvListener = object : WeatherRVAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(this@OutputActivity, WeatherActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("id", Weath[position].id)
                index = position
                startActivity(intent)
            }
        }

        adapter.setOnClickListener(rvListener)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

}