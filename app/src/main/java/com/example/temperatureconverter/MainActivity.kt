package com.example.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var seekBarCelsius: SeekBar
    private lateinit var seekBarFahrenheit: SeekBar
    private lateinit var textViewCelsiusValue: TextView
    private lateinit var textViewFahrenheitValue: TextView
    private lateinit var textViewInterestingMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarCelsius = findViewById(R.id.seekBarCelsius)
        seekBarFahrenheit = findViewById(R.id.seekBarFahrenheit)
        textViewCelsiusValue = findViewById(R.id.textViewCelsiusValue)
        textViewFahrenheitValue = findViewById(R.id.textViewFahrenheitValue)
        textViewInterestingMessage = findViewById(R.id.textViewInterestingMessage)

        seekBarFahrenheit.max = 212

        seekBarCelsius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val fahrenheitValue = celsiusToFahrenheit(progress)
                    updateFahrenheit(fahrenheitValue)
                    textViewCelsiusValue.text = "$progress°C"
                    updateInterestingMessage(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarFahrenheit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (progress < 32) {
                        seekBar?.progress = 32 // This line prevents the Fahrenheit SeekBar from going below 32
                        updateCelsius(0) // Updates the Celsius value accordingly
                    } else {
                        val celsiusValue = fahrenheitToCelsius(progress)
                        updateCelsius(celsiusValue)
                        textViewFahrenheitValue.text = String.format("%.2f°F", progress.toFloat())
                        updateInterestingMessage(celsiusValue)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Initialize the views with default values
        val initialCelsius = seekBarCelsius.progress
        updateFahrenheit(celsiusToFahrenheit(initialCelsius))
        textViewCelsiusValue.text = "$initialCelsius°C" // Initialize Celsius text view
        updateInterestingMessage(initialCelsius)
    }

    private fun celsiusToFahrenheit(celsius: Int): Float {
        return (celsius * 9/5f) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return ((fahrenheit - 32) * 5/9f).roundToInt()
    }

    private fun updateFahrenheit(fahrenheit: Float) {
        textViewFahrenheitValue.text = String.format("%.2f°F", fahrenheit)
        seekBarFahrenheit.progress = fahrenheit.roundToInt()
    }

    private fun updateCelsius(celsius: Int) {
        textViewCelsiusValue.text = "$celsius°C"
        seekBarCelsius.progress = celsius
    }

    private fun updateInterestingMessage(celsius: Int) {
        val message = if (celsius <= 20) "I wish it were warmer." else "I wish it were colder."
        textViewInterestingMessage.text = message
    }
}
