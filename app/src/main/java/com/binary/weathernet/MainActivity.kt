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
    private lateinit var tvPrbRain1: TextView
    private lateinit var tvPrbRain2: TextView
    private lateinit var tvPrbRain3: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        tvTempVal1 = findViewById(R.id.tvTempVal1)
        tvHumiVal1 = findViewById(R.id.tvHumiVal1)
        tvRainIs1 = findViewById(R.id.tvRainIs1)
        tvPrbRain1 = findViewById(R.id.tvPrbRain1)
        tvTempVal2 = findViewById(R.id.tvTempVal2)
        tvHumiVal2 = findViewById(R.id.tvHumiVal2)
        tvRainIs2 = findViewById(R.id.tvRainIs2)
        tvPrbRain2 = findViewById(R.id.tvPrbRain2)
        tvTempVal3 = findViewById(R.id.tvTempVal3)
        tvHumiVal3 = findViewById(R.id.tvHumiVal3)
        tvRainIs3 = findViewById(R.id.tvRainIs3)
        tvPrbRain3 = findViewById(R.id.tvPrbRain3)

        database = FirebaseDatabase.getInstance().getReference("Data")

        fetchData()
    }
    fun isRaining(temperature: Double, humidity: Double): Boolean {
        val highHumidityThreshold = 80.0
        val lowTemperatureThreshold = 25.0
        return if (humidity > highHumidityThreshold && temperature < lowTemperatureThreshold) {
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
                    Log.d("FirebaseSnapshot", "Full Snapshot: ${snapshot.value}")

                    val temperatureSnap = snapshot.child("temperature").value
                    val humiditySnap = snapshot.child("Humidity").value
                    val rainSnap = snapshot.child("Rain").value

                    val temperatureSnap2 = snapshot.child("temperature").value
                    val humiditySnap2 = snapshot.child("Humidity").value
                    val rainSnap2 = snapshot.child("Rain").value

                    val temperatureSnap3 = snapshot.child("temperature").value
                    val humiditySnap3 = snapshot.child("Humidity").value
                    val rainSnap3 = snapshot.child("Rain").value

                    Log.d("FirebaseSnapshot", "Temperature Snapshot: $temperatureSnap")
                    Log.d("FirebaseSnapshot", "Humidity Snapshot: $humiditySnap")
                    Log.d("FirebaseSnapshot", "Rain Snapshot: $rainSnap")

                    Log.d("FirebaseSnapshot", "Temperature Snapshot: $temperatureSnap2")
                    Log.d("FirebaseSnapshot", "Humidity Snapshot: $humiditySnap2")
                    Log.d("FirebaseSnapshot", "Rain Snapshot: $rainSnap2")

                    Log.d("FirebaseSnapshot", "Temperature Snapshot: $temperatureSnap3")
                    Log.d("FirebaseSnapshot", "Humidity Snapshot: $humiditySnap3")
                    Log.d("FirebaseSnapshot", "Rain Snapshot: $rainSnap3")

                    val raining = isRaining(temperatureSnap as Double, humiditySnap as Double)

                    if (raining) {
                        tvPrbRain1.text=("likely to rain.")
                    } else {
                        tvPrbRain1.text=("unlikely to rain.")
                    }

                    val raining2 = isRaining(temperatureSnap2 as Double, humiditySnap2 as Double)

                    if (raining2) {
                        tvPrbRain1.text=("likely to rain.")
                    } else {
                        tvPrbRain1.text=("unlikely to rain.")
                    }

                    val raining3 = isRaining(temperatureSnap3 as Double, humiditySnap3 as Double)

                    if (raining3) {
                        tvPrbRain1.text=("likely to rain.")
                    } else {
                        tvPrbRain1.text=("unlikely to rain.")
                    }

                    tvTempVal1.text = "${temperatureSnap?.toString()}℃"
                    tvHumiVal1.text = "${humiditySnap?.toString()}%"
                    tvRainIs1.text = rainSnap?.toString() ?: "N/A"

                    tvTempVal2.text = "${temperatureSnap2?.toString() ?: "N/A"}℃"
                    tvHumiVal2.text = "${humiditySnap2?.toString() ?: "N/A"}%"
                    tvRainIs2.text = rainSnap2?.toString() ?: "N/A"

                    tvTempVal3.text = "${temperatureSnap3?.toString() ?: "N/A"}℃"
                    tvHumiVal3.text = "${humiditySnap3?.toString() ?: "N/A"}%"
                    tvRainIs3.text = rainSnap3?.toString() ?: "N/A"

                } catch (e: Exception) {
                    Log.e("FirebaseError", "Error converting data: ${e.message}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Database error: ${error.message}")
            }
        })
    }
}
