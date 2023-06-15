package com.capstone.sata.data.remote

import androidx.annotation.BinderThread
import com.capstone.sata.data.model.FilterResponse
import com.capstone.sata.data.model.PostImgResponse
import com.capstone.sata.data.model.ProductResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SataAppService {
    @POST ("alam")
    suspend fun postImgResponseAlam(
        @Part file : MultipartBody.Part
    ):PostImgResponse

    @POST ("buatan")
    suspend fun postImgResponseBuatan(
        @Part file : MultipartBody.Part
    ):PostImgResponse

    @POST ("filter")
    suspend fun postFilter(
        @Body json : JsonObject
    ):FilterResponse

    @GET ("products/byId/{id}")
    suspend fun getProductbyId(
        @Path ("id")id:String
    ):ProductResponse

}