package com.capstone.sata.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.adapter.FilterAdapter
import com.capstone.sata.databinding.ActivityFilterBinding
import com.capstone.sata.dummy.DataDummy.generateDataDummy

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.rvFilter.layoutManager = LinearLayoutManager(this)
        val data = generateDataDummy()
        binding.rvFilter.setHasFixedSize(true)
        binding.rvFilter.adapter = FilterAdapter(data)
    }
}