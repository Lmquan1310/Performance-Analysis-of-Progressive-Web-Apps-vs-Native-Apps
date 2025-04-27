package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sierpinskiView: SierpinskiView
    private lateinit var timerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sierpinskiView = findViewById(R.id.sierpinskiView)
        val maxDepthInput = findViewById<EditText>(R.id.maxDepthInput)
        val drawButton = findViewById<Button>(R.id.drawButton)
        timerTextView = findViewById(R.id.timerTextView)

        // Set up the callback for drawing completion
        sierpinskiView.onDrawComplete = { timeMs ->
            timerTextView.text = "Drawing completed in ${timeMs}ms"
        }

        drawButton.setOnClickListener {
            val depth = maxDepthInput.text.toString().toIntOrNull()

            if (depth != null && depth in 0..20) {
                timerTextView.text = "Drawing..." // Show that drawing has started
                sierpinskiView.setDepth(depth)
            } else {
                Toast.makeText(this, "Enter a valid depth between 0 and 20", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
