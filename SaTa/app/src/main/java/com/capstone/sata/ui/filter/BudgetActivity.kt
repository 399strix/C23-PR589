package com.capstone.sata.ui.filter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.adapter.FilterAdapter
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.databinding.ActivityBudgetBinding
import com.capstone.sata.dummy.DataDummy.generateDataDummy

class BudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBudgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showRecyclerView()
    }

    private fun showRecyclerView() {
        val data = generateDataDummy()
        val adapter = FilterAdapter(data)
        binding.rvFilter.layoutManager = LinearLayoutManager(this)
        binding.rvFilter.setHasFixedSize(true)
        binding.rvFilter.adapter = adapter

        adapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataFilter) {
                startActivity(Intent(this@BudgetActivity, PlaceActivity::class.java))
            }
        })
    }
}