package ru.k0ras1k.ethereally.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.k0ras1k.ethereally.R

class Intro0 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_0)

        val button = findViewById<ImageButton>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, Intro1::class.java)
            startActivity(intent)
            finish()
        }
    }
}