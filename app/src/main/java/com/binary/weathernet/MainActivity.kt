package com.binary.weathernet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.view.WindowManager
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var tvTempVal1: TextView
    private lateinit var tvHumiVal1: TextView
    private lateinit var tvRainIs1: TextView
    private lateinit var tvTempVal2: TextView
    private lateinit var tvHumiVal2: TextView
    private lateinit var tvRainIs2: TextView
    private lateinit var tvTempVal3: TextView
    private lateinit var tvHumiVal3: TextView
    private lateinit var tvRainIs3: TextView
    private lateinit var tvPrbRainIs1: TextView
    private lateinit var tvPrbRainIs2: TextView
    private lateinit var tvPrbRainIs3: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        tvTempVal1 = findViewById(R.id.tvTempVal1)
        tvHumiVal1 = findViewById(R.id.tvHumiVal1)
        tvRainIs1 = findViewById(R.id.tvRainIs1)
        tvPrbRainIs1 = findViewById(R.id.tvPrbRainIs1)
        tvTempVal2 = findViewById(R.id.tvTempVal2)
        tvHumiVal2 = findViewById(R.id.tvHumiVal2)
        tvRainIs2 = findViewById(R.id.tvRainIs2)
        tvPrbRainIs2 = findViewById(R.id.tvPrbRainIs2)
        tvTempVal3 = findViewById(R.id.tvTempVal3)
        tvHumiVal3 = findViewById(R.id.tvHumiVal3)
        tvRainIs3 = findViewById(R.id.tvRainIs3)
        tvPrbRainIs3 = findViewById(R.id.tvPrbRainIs3)

        database = FirebaseDatabase.getInstance().getReference("Data")

        fetchData()
    }
    fun isRaining(temperature: Double, humidity: Double): Boolean {
        val highHumidityThreshold = 80.0
        val lowTemperatureThreshold = 25.0

        return if (humidity > highHumidityThreshold && temperature < lowTemperatureThreshold || highHumidityThreshold <= humidity) {
            true
        } else {
            false
        }
    }
    private fun fetchData() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    // Extract values safely
                    val temperatureSnap = snapshot.child("temperature").value?.toString()?.toDoubleOrNull()
                    val humiditySnap = snapshot.child("Humidity").value?.toString()?.toDoubleOrNull()
                    val rainSnap = snapshot.child("Rain").value?.toString()

                    val temperatureSnap2 = snapshot.child("temperature2").value?.toString()?.toDoubleOrNull()
                    val humiditySnap2 = snapshot.child("Humidity2").value?.toString()?.toDoubleOrNull()
                    val rainSnap2 = snapshot.child("Rain2").value?.toString()

                    val temperatureSnap3 = snapshot.child("temperature3").value?.toString()?.toDoubleOrNull()
                    val humiditySnap3 = snapshot.child("Humidity3").value?.toString()?.toDoubleOrNull()
                    val rainSnap3 = snapshot.child("Rain3").value?.toString()

                    // Check for null values before proceeding
                    if (temperatureSnap != null && humiditySnap != null) {
                        val raining = isRaining(temperatureSnap, humiditySnap)
                        tvPrbRainIs1.text = if (raining) "likely to rain." else "unlikely to rain."
                        tvTempVal1.text = "%.2f℃".format(temperatureSnap)
                        tvHumiVal1.text = "$humiditySnap%"
                        tvRainIs1.text = rainSnap ?: "N/A"
                    }

                    if (temperatureSnap2 != null && humiditySnap2 != null) {
                        val raining2 = isRaining(temperatureSnap2, humiditySnap2)
                        tvPrbRainIs2.text = if (raining2) "likely to rain." else "unlikely to rain."
                        tvTempVal2.text = "%.2f℃".format(temperatureSnap2)
                        tvHumiVal2.text = "$humiditySnap2%"
                        tvRainIs2.text = rainSnap2 ?: "N/A"
                    }

                    if (temperatureSnap3 != null && humiditySnap3 != null) {
                        val raining3 = isRaining(temperatureSnap3, humiditySnap3)
                        tvPrbRainIs3.text = if (raining3) "likely to rain." else "unlikely to rain."
                        tvTempVal3.text = "%.2f℃".format(temperatureSnap3)
                        tvHumiVal3.text = "$humiditySnap3%"
                        tvRainIs3.text = rainSnap3 ?: "N/A"
                    }

                } catch (e: Exception) {
                    Log.e("FirebaseError", "Error converting data: ${e.message}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
