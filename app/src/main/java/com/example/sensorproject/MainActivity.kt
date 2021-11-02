package com.example.sensorproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sensorproject.databinding.ActivityMainBinding
import android.widget.RelativeLayout


class MainActivity : AppCompatActivity(), SensorEventListener {


    private lateinit var bindig: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    var state = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindig.root)

        SetupAcc()
        SetupLight()
    }

    private fun SetupAcc()
    {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_NORMAL)

        }
    }

    private fun SetupLight()
    {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)?.also {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_NORMAL)

        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER)
        {
            if(event.values[0] > 10)
            {
                when(state){
                    0 -> {
                        bindig.ZippoState.setImageResource(R.drawable.openzippo)
                        state = 1
                    }
                    1 ->{
                        bindig.ZippoState.setImageResource(R.drawable.closedzippo)
                        state = 0
                    }
                    2 -> {
                        bindig.ZippoState.setImageResource(R.drawable.closedzippo)
                        state = 0
                    }
                }
            }


        }

        if(event?.sensor?.type == Sensor.TYPE_LIGHT)
        {
            if(event.values[0] < 2)
            {
                when(state){
                    0 -> {

                    }
                    1 -> {
                        bindig.ZippoState.setImageResource(R.drawable.zippofire)
                        bindig.root.setBackgroundColor(Color.BLACK)
                        state = 2;
                    }
                }
            }
            if(event.values[0] > 2 && state==2) {
                bindig.ZippoState.setImageResource(R.drawable.openzippo)
                bindig.root.setBackgroundColor(Color.WHITE)
                state=1
            }

        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("kenyér")
    }
}