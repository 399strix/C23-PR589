package com.capstone.sata.ui.cam

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.sata.ViewModelFactory
import com.capstone.sata.data.model.ImageResponse
import com.capstone.sata.databinding.ActivityCameraBinding
import com.capstone.sata.ui.filter.BudgetActivity
import com.capstone.sata.utils.Result
import com.capstone.sata.utils.reduceImageSize
import com.capstone.sata.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class CameraActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCameraBinding
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var factory: ViewModelFactory
    private var file : File?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        factory = ViewModelFactory.getInstance(this)
        cameraViewModel = ViewModelProvider(this, factory)[CameraViewModel::class.java]
        openGallery()
        toFilterActivity()
    }

    private fun toFilterActivity() {
        binding.btnNext.setOnClickListener {
            if (file != null) {
                val file = reduceImageSize(file as File)
                val reqImgFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image", file.name,reqImgFile)
                val id = intent.getIntExtra("TAG_CAMERA", 0)
                when(id) {
                    1 -> {
                        cameraViewModel.postImgResponseAlam(imageMultipart).observe(this) {
                            when(it) {
                                is Result.Success -> {
                                    toBudget(it.data)
                                }
                                is Result.Loading -> {

                                }
                                is Result.Error -> {
                                    Log.d("Erorr", it.error)
                                }
                            }
                        }
                    }
                    2 -> {
                        cameraViewModel.postImgResponseBuatan(imageMultipart).observe(this) {
                            when(it) {
                                is Result.Success -> {
                                    toBudget(it.data)
                                }
                                is Result.Loading -> {

                                }
                                is Result.Error -> {
                                    Log.d("Erorr", it.error)
                                }
                            }
                        }
                    }
                }
            } else {
                binding.btnNext.error = "Masukkan gambar terlebih dahulu!"
            }
        }

    }

    private fun toBudget(data: ImageResponse) {
        val intent = Intent(this, BudgetActivity :: class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@CameraActivity)
                binding.ivInsert.setImageURI(uri)
                file = myFile
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

    companion object {
        const val TAG_CAMERA = "TAG_CAMERA"
    }
}