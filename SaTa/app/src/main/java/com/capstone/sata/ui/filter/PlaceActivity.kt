package com.capstone.sata.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.R
import com.capstone.sata.adapter.SataAdapter
import com.capstone.sata.data.model.Answer
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ActivityPlaceBinding
import com.capstone.sata.dummy.DataDummy

class PlaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceBinding
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userPreferences = UserPreferences(this@PlaceActivity)
        showRecyclerView()

    }

    private fun showRecyclerView() {
        binding.rvFilter.layoutManager = LinearLayoutManager(this)
        val data = DataDummy.listAnswer()
        val placeAdapter = SataAdapter(data)
        binding.rvFilter.setHasFixedSize(true)
        binding.rvFilter.adapter = placeAdapter

        placeAdapter.setOnItemClickCallback(object : SataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Answer) {
                Toast.makeText(this@PlaceActivity,userPreferences.getUser().toString(), Toast.LENGTH_LONG).show()
            }


        })

    }
}