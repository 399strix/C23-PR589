package com.capstone.sata.ui.recom

import androidx.lifecycle.ViewModel
import com.capstone.sata.data.repo.SataRepo
import com.google.gson.JsonObject
import org.json.JSONObject

class RecomViewModel(private val repo: SataRepo): ViewModel() {
    fun getProductbyId (id : Int) = repo.getProductbyId(id)
    fun postFilter (jsonObject: JsonObject) = repo.postFilter(jsonObject)
}
