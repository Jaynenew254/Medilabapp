package com.JaneWanjiru.medilab

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class Screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch skip 1
        val skip= findViewById<MaterialTextView>(R.id.skip1)
        skip.setOnClickListener {
            val intent = Intent(applicationContext,MyCart::class.java)
            startActivity(intent)
            finish()

        }
        val btn =findViewById<FloatingActionButton>(R.id.fab)
        btn .setOnClickListener {
            val intent =Intent(applicationContext,Screen2::class.java)
            startActivity(intent)
        }
    }
}