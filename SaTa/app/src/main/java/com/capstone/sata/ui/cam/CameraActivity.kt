package com.capstone.sata.ui.cam

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.sata.databinding.ActivityCameraBinding
import com.capstone.sata.ui.filter.BudgetActivity
import com.capstone.sata.utils.uriToFile

class CameraActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        openGallery()
        toFilterActivity()
    }

    private fun toFilterActivity() {
        binding.btnNext.setOnClickListener{
            startActivity(Intent(this@CameraActivity, BudgetActivity::class.java))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@CameraActivity)
                binding.ivInsert.setImageURI(uri)
            }
        }
    }

    private fun toInsertCamera() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun openGallery() {
        binding.ivInsert.setOnClickListener{
            toInsertCamera()
        }
    }
}