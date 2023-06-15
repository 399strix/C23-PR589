package com.capstone.sata.data.model

import com.google.gson.annotations.SerializedName

data class DataFilter(
    val imgUrl : String,
    val tvDesc : String,
)

data class DataQuestion(
    val dataQuestion : DataQuestion?,
    val answer: List<Answer>,
    val isAvail : Boolean,
    val questionTitle: String
)

data class Answer(
    val answer: String,
)

data class DataPreferences(
    var jenisWisata : Boolean? = null,
    var budget : Int? = null,
    var daerah : String? = null
)
data class Response(
    var msg : String
)

data class PostImgResponse(

    @field:SerializedName("city")
    val city: List<String>,

    @field:SerializedName("price")
    val price: List<String>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("predictions")
    val predictions: List<Int>,

    @field:SerializedName("status")
    val status:String
)

data class ProductRequest(
    var id : Int,
    var label : Int,
    var email : String,
    var name : String? = null,
    var description : String? = null,
    var location : String,
    var image : String,
    var price : Int
)
data class ProductResponse(
    var id : Int,
    var label : Int,
    var email : String,
    var name : String? = null,
    var description : String? = null,
    var location : String,
    var image : String,
    var price : Int
)

data class FilterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("recommendations")
    val recommendations: List<String?>? = null,

    @field:SerializedName("status")
    val status: String?=null
)