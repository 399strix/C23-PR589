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

override fun onClick(v: View) {
    if (v.id == R.id.btn_choose) {
        if (rgNumber.checkedRadioButtonId > 0) {
            var value = 0
            when (rgNumber.checkedRadioButtonId) {
                R.id.rb_50 -> value = 50

                R.id.rb_100 -> value = 100

                R.id.rb_150 -> value = 150

                R.id.rb_200 -> value = 200
            }

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SELECTED_VALUE, value)
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }
}