package com.capstone.sata.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class ImageResponse(

    @field:SerializedName("predictions_confidence")
    val predictionsConfidence: PredictionsConfidence,

    @field:SerializedName("city")
    val city: List<String>,

    @field:SerializedName("price")
    val price: List<String>,

    @field:SerializedName("predictions_label")
    val predictionsLabel: List<Int>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
) : Parcelable

@Parcelize
data class PredictionsConfidence(

    @field:SerializedName("Taman")
    val taman: Double,

    @field:SerializedName("Peternakan")
    val peternakan: Double
):Parcelable

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

data class RecommendationsItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
data class FilterResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("recommendations")
    val recommendations: List<RecommendationsItem>,

    @field:SerializedName("status")
    val status: String
)