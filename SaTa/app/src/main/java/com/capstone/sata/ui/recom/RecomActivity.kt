package com.capstone.sata.ui.recom

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.ViewModelFactory
import com.capstone.sata.adapter.FilterAdapter
import com.capstone.sata.adapter.SataAdapter
import com.capstone.sata.data.model.FilterResponse
import com.capstone.sata.data.model.ImageResponse
import com.capstone.sata.data.model.RecommendationsItem
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ActivityRecomBinding
import com.capstone.sata.ui.filter.PlaceActivity
import com.google.gson.JsonObject
import com.capstone.sata.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject

class RecomActivity : AppCompatActivity() {
    private lateinit var recomViewModel: RecomViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var userPreferences: UserPreferences
    private lateinit var binding: ActivityRecomBinding
    private var list: ArrayList<String>? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userPreferences = UserPreferences(this)
        factory = ViewModelFactory.getInstance(this)
        recomViewModel = ViewModelProvider(this, factory)[RecomViewModel::class.java]

        getList()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getList() {
        val prediction = intent.getParcelableExtra<ImageResponse>("data")!!.predictionsLabel
        val budget = userPreferences.getUser().budget
        val daerah = userPreferences.getUser().daerah

        val json = JsonObject()
        val predictionJsonArray = JsonArray()
        prediction.forEach { value ->
            predictionJsonArray.add(value)
        }
        json.add("prediction", predictionJsonArray)
        json.addProperty("price", budget)
        json.addProperty("location", daerah)
        val gson = Gson()
        val jsonString = gson.toJson(json)

        Log.d("Answer", jsonString)

        recomViewModel.postFilter(json).observe(this) {
            when(it) {
                is Result.Success -> {
                    showRecyclerView(it.data )
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    private fun showRecyclerView(list:FilterResponse) {

        val adapter = FilterAdapter(list.recommendations)
        binding.rvRecom.layoutManager = LinearLayoutManager(this)
        binding.rvRecom.setHasFixedSize(true)
        binding.rvRecom.adapter = adapter

        adapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RecommendationsItem) {
            val intent = Intent (this@RecomActivity, DetailRecomActivity::class.java)
                intent.putExtra(DetailRecomActivity.TAG_ID, data.id)
                startActivity(intent)
         }

        })
    }


}