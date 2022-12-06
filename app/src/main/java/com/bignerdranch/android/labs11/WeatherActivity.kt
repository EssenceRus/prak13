package com.bignerdranch.android.labs11

import android.R
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.WeatherDAO
import com.bignerdranch.android.labs11.data.WeatherDatabase
import com.bignerdranch.android.labs11.data.model.CitiesTable
import com.bignerdranch.android.labs11.data.model.WeatherTable
import com.bignerdranch.android.labs11.databinding.ActivityWeatherBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors

private lateinit var binding: ActivityWeatherBinding
private lateinit var db: WeatherDatabase
private lateinit var weatherDAO: WeatherDAO
private var citiesTable: MutableList<CitiesTable> = mutableListOf()
private var weatherTable: MutableList<WeatherTable> = mutableListOf()
private var cities: MutableList<String> = mutableListOf()
private var date : String? = null


private val Weath : MutableList<WeatherTable> = mutableListOf()
private val Cities : MutableList<CitiesTable> = mutableListOf()


private var index: Int = -1
private var CitiesId: Int = 0
private var id: Int = 0
class WeatherActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
        weatherDAO = db.weatherDAO()

        var spinnerAdapter: ArrayAdapter<String>
        val CitiesT = weatherDAO.getAllCities()
        CitiesT.observe(this){
            var tempCities = mutableListOf<String>()
            it.forEach{
                tempCities.add(it.city.toString())
                spinnerAdapter = ArrayAdapter<String>(this,R.layout.simple_list_item_1, tempCities)
                binding.spinner.adapter = spinnerAdapter
            }


        }






        index = intent.getIntExtra("index",-1)
        CitiesId  = intent.getIntExtra("id",0)
        val cl = Calendar.getInstance()






        if(index!=-1)
        {
            db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
            weatherDAO = db.weatherDAO()
            binding.delButton.isVisible = true
            DbGet()
            val Weathers = weatherDAO.getWeath(CitiesId)
            Weathers.observe(this){
                binding.btn03.setText("Изменить")
                it.forEach{
                    binding.editTextTextPersonName2.setText(it.Daytime_Temperature.toString())
                    binding.editTextTextPersonName3.setText(it.NightTemp.toString())




                    binding.spinner.setSelection(it.citiesId)
                    id = it.id

                }

            }


        }
        else{
            binding.delButton.isVisible = false
        }
        binding.btn03.setOnClickListener {
            db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
            weatherDAO = db.weatherDAO()
            if(index==-1) {
                val DayTemp:String =binding.editTextTextPersonName2.text.toString();
                val NightTemp:String =binding.editTextTextPersonName3.text.toString();

                DbGet()
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {


                    weatherDAO.addWeather(WeatherTable(0,
                        binding.spinner.selectedItemPosition,

                        DayTemp.toString(),
                        NightTemp.toString()))

                }

            }
            else if(index!=-1){
                val DayTemp:String =binding.editTextTextPersonName2.text.toString();
                val NightTemp:String =binding.editTextTextPersonName3.text.toString();
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {


                    weatherDAO.saveWeather(WeatherTable(id,
                        binding.spinner.selectedItemPosition,

                        DayTemp.toString(),
                        NightTemp.toString()))


                }

            }

            binding.editTextTextPersonName2.setText("")
            binding.editTextTextPersonName3.setText("")

        }
        binding.delButton.setOnClickListener{
            val DayTemp:String =binding.editTextTextPersonName2.text.toString();
            val NightTemp:String =binding.editTextTextPersonName3.text.toString();
            val exec = Executors.newSingleThreadExecutor()
            exec.execute {
                weatherDAO.killWeather(WeatherTable(id,
                    binding.spinner.selectedItemPosition,

                    DayTemp.toString(),
                    NightTemp.toString()))

            }
            val intent = Intent(this, OutputActivity::class.java)
            startActivity((intent))
        }


    }
    fun DbGet()
    {
        db = Room.databaseBuilder(this,WeatherDatabase::class.java, DATABASE_NAME).build()
        weatherDAO = db.weatherDAO()
        Weath.clear()
        Cities.clear()
        val weathers = weatherDAO.getAllWeather()
        val CitiesT = weatherDAO.getAllCities()
        CitiesT.observe(this){
            citiesTable.addAll(it)
            //cities.addAll(tempCities)

        }

        weathers.observe(this){
            weatherTable.addAll(it)
        }
    }


}