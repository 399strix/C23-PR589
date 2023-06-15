package com.capstone.sata.ui.recom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.R
import com.capstone.sata.adapter.SataAdapter
import com.capstone.sata.data.model.Answer
import com.capstone.sata.databinding.ActivityRecomBinding
import com.capstone.sata.dummy.DataDummy
import com.capstone.sata.ui.filter.PlaceActivity

class RecomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recom)

        binding = ActivityRecomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showRecyclerView()
    }

    private fun showRecyclerView() {
        val data = DataDummy.listAnswer()
        val adapter = SataAdapter(data)
        binding.rvRecom.layoutManager = LinearLayoutManager(this)
        binding.rvRecom.setHasFixedSize(true)
        binding.rvRecom.adapter = adapter

        adapter.setOnItemClickCallback(object : SataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Answer) {
                startActivity(Intent(this@RecomActivity, PlaceActivity::class.java))
            }

        })
    }


}