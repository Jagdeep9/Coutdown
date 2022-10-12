package com.example.countdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    lateinit var countDown: Chronometer
    var running= false
    var offset: Long = 0

    val OFFSET_KEY="offest"
    val RUNNING_KEY="running"
    val BASE_KEY="base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countDown = findViewById<Chronometer>(R.id.countdown)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                countDown.base = savedInstanceState.getLong(BASE_KEY)
                countDown.start()
            } else {
                setBaseTime()
            }
        }
        val startButton= findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if (!running){
                setBaseTime()
                countDown.start()
                running= true

                val editText=findViewById<EditText>(R.id.edit_View1)
                val input = Integer.parseInt(editText.text.toString())
                countDown.base += input*1000
            }
        }

        val pauseButton=findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener{
            if(running){
                saveOffset()
                countDown.stop()

                running= false
            }
        }
        val resetButton =findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
        }
        override fun onSaveInstanceState(savedInstanceState: Bundle) {
            savedInstanceState.putLong(OFFSET_KEY, offset)
            savedInstanceState.putBoolean(RUNNING_KEY, running)
            savedInstanceState.putLong(BASE_KEY, countDown.base)
            super.onSaveInstanceState(savedInstanceState)

    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveOffset()
            countDown.stop()

        }
    }
    override fun onResume() {
        super.onResume()
        if(running){
            countDown.start()
            offset= 0
        }
    }
    private fun saveOffset(){
        offset = SystemClock.elapsedRealtime() -countDown.base
    }
    private fun setBaseTime(){
        countDown.base=SystemClock.elapsedRealtime() -offset
    }
}