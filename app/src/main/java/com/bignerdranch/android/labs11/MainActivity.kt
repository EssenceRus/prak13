package com.bignerdranch.android.labs11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private lateinit var btnIn : Button
private lateinit var btnOut : Button
private  lateinit var btnAddCity : Button
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIn = findViewById(R.id.button)
        btnOut = findViewById(R.id.button2)
        btnAddCity = findViewById(R.id.button4)
        btnIn.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity((intent))
        }
        btnOut.setOnClickListener {
            val intent = Intent(this, OutputActivity::class.java)
            startActivity((intent))
        }
        btnAddCity.setOnClickListener{
            val intent = Intent(this, CitiesAddActivity::class.java)
            startActivity((intent))
        }


    }
}