package com.capstone.sata.ui.cam

import androidx.lifecycle.ViewModel
import com.capstone.sata.data.repo.SataRepo
import okhttp3.MultipartBody

class CameraViewModel(private val repo: SataRepo): ViewModel() {
    fun postImgResponseAlam (file : MultipartBody.Part) = repo.postImgResponseAlam(file)
    fun postImgResponseBuatan (file : MultipartBody.Part) = repo.postImgResponseBuatan(file)
}