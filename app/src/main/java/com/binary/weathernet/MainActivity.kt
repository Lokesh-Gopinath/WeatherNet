package com.binary.weathernet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var tvTempVal1: TextView
    private lateinit var tvHumiVal1: TextView
    private lateinit var tvRainIs1: TextView
    private lateinit var tvTempVal2: TextView
    private lateinit var tvHumiVal2: TextView
    private lateinit var tvRainIs2: TextView
    private lateinit var btnGetData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTempVal1 = findViewById(R.id.tvTempVal1)
        tvHumiVal1 = findViewById(R.id.tvHumiVal1)
        tvRainIs1 = findViewById(R.id.tvRainIs1)
        tvTempVal2 = findViewById(R.id.tvTempVal2)
        tvHumiVal2 = findViewById(R.id.tvHumiVal2)
        tvRainIs2 = findViewById(R.id.tvRainIs2)

        btnGetData = findViewById(R.id.btnGetData)

        database = FirebaseDatabase.getInstance().getReference("Data")


        fetchData()
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

                    Log.d("FirebaseSnapshot", "Temperature Snapshot: $temperatureSnap")
                    Log.d("FirebaseSnapshot", "Humidity Snapshot: $humiditySnap")
                    Log.d("FirebaseSnapshot", "Rain Snapshot: $rainSnap")
                    Log.d("FirebaseSnapshot", "Temperature Snapshot: $temperatureSnap2")
                    Log.d("FirebaseSnapshot", "Humidity Snapshot: $humiditySnap2")
                    Log.d("FirebaseSnapshot", "Rain Snapshot: $rainSnap2")

                    tvTempVal1.text = "${temperatureSnap?.toString() ?: "N/A"}℃"
                    tvHumiVal1.text = "${humiditySnap?.toString() ?: "N/A"}%"
                    tvRainIs1.text = rainSnap?.toString() ?: "N/A"
                    tvTempVal2.text = "${temperatureSnap2?.toString() ?: "N/A"}℃"
                    tvHumiVal2.text = "${humiditySnap2?.toString() ?: "N/A"}%"
                    tvRainIs2.text = rainSnap2?.toString() ?: "N/A"

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
