package com.capstone.sata.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.sata.data.model.FilterResponse
import com.capstone.sata.data.model.ImageResponse
import com.capstone.sata.data.model.PostImgResponse
import com.capstone.sata.data.model.ProductRequest
import com.capstone.sata.data.model.ProductResponse
import com.capstone.sata.data.model.Response
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.data.remote.SataAppService
import com.capstone.sata.data.remote.SataAppServices
import com.capstone.sata.utils.Result
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import org.json.JSONObject

class SataRepo (private val pref: UserPreferences, private val apiService: SataAppService, private val apiServices: SataAppServices){
    fun postImgResponseAlam(file : MultipartBody.Part) : LiveData<Result<ImageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService
                .postImgResponseAlam(file)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Response_Alam", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postImgResponseBuatan(file : MultipartBody.Part) : LiveData<Result<ImageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService
                .postImgResponseBuatan(file)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Response_Buatan", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postFilter(jsonObject: JsonObject) : LiveData<Result<FilterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService
                .postFilter(jsonObject)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Response_FIlter", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProductbyId(id: Int) : LiveData<Result<ProductResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiServices
                .getProductbyId(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Product_Response", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}