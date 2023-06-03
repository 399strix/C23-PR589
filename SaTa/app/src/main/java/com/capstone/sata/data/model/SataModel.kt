package com.capstone.sata.data.model

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
