package com.capstone.sata.ui.recom

import androidx.lifecycle.ViewModel
import com.capstone.sata.data.model.ProductRequest
import com.capstone.sata.data.repo.SataRepo
import com.google.gson.JsonObject

class RecomViewModel(private val repo: SataRepo): ViewModel() {
    fun getProductbyId (id : String) = repo.getProductbyId(id)
}
