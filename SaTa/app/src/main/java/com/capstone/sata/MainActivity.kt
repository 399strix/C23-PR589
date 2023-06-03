package com.capstone.sata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sata.databinding.ActivityMainBinding
import com.capstone.sata.ui.filter.FilterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toWisataBuatan()
        toWisataAlam()

    }

    private fun toWisataAlam() {
        binding.btnAlam.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterActivity::class.java))
        }
    }

    private fun toWisataBuatan() {
        binding.btnBuatan.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterActivity::class.java))
        }
    }
}