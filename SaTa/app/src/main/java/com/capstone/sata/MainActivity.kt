package com.capstone.sata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ActivityMainBinding
import com.capstone.sata.ui.cam.CameraActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        userPreferences = UserPreferences(this@MainActivity)
        toWisataBuatan()
        toWisataAlam()

    }

    private fun toWisataAlam() {
        binding.btnAlam.setOnClickListener {
            userPreferences.setJenisWisata(true)
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }
    }

    private fun toWisataBuatan() {
        userPreferences.setJenisWisata(false)
        binding.btnBuatan.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }
    }
}