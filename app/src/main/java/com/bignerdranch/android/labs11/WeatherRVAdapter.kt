package com.bignerdranch.android.labs11

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.labs11.data.model.CitiesTable
import com.bignerdranch.android.labs11.data.model.WeatherTable

class WeatherRVAdapter(context: Context?, val data: MutableList<WeatherTable>,val cities: MutableList<CitiesTable>): RecyclerView.Adapter<WeatherRVAdapter.WeatherViewHolder?>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var iClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_weather,parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = data[position]
        val itemc = cities[data[position].citiesId]

        holder.dayTemp.text = item.Daytime_Temperature.toString()
        holder.nightTemp.text = item.NightTemp.toString()
        holder.City.text = itemc.city
    }

    override fun getItemCount(): Int = data.size
    inner class WeatherViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener {

        var dayTemp = item.findViewById<TextView>(R.id.Day_Temp)
        var nightTemp = item.findViewById<TextView>(R.id.Night_Temp)
        var City = item.findViewById<TextView>(R.id.City)
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?){
            iClickListener?.onItemClick(view,adapterPosition)
        }
    }
    fun setOnClickListener(itemClickListener: ItemClickListener?){
        iClickListener = itemClickListener
    }
    interface ItemClickListener {
        fun onItemClick(view: View?,position: Int)
    }
}