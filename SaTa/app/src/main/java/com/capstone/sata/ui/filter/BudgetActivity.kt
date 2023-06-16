package com.capstone.sata.ui.filter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sata.adapter.SataAdapter
import com.capstone.sata.data.model.Answer
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.data.model.DataPreferences
import com.capstone.sata.data.model.ImageResponse
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ActivityBudgetBinding
import com.capstone.sata.dummy.DataDummy.generateDataDummy
import com.capstone.sata.dummy.DataDummy.listAnswer

class BudgetActivity : AppCompatActivity() {
    private lateinit var userPref: UserPreferences
    private lateinit var binding: ActivityBudgetBinding
    private var data: ImageResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userPref = UserPreferences(this)
        data = intent.getParcelableExtra("data")
        showRecyclerView()
    }

    private fun showRecyclerView() {
        val newData = this.data
        val adapter = SataAdapter(newData!!.price as ArrayList<String>)
        binding.rvFilter.layoutManager = LinearLayoutManager(this)
        binding.rvFilter.setHasFixedSize(true)
        binding.rvFilter.adapter = adapter

        adapter.setOnItemClickCallback(object : SataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String, position: Int) {
                userPref.setBudget(position)
                val intent = Intent(this@BudgetActivity, PlaceActivity::class.java)
                intent.putExtra("data", newData)
                startActivity(intent)
            }


        })
    }
}