package com.capstone.sata.ui.recom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.capstone.sata.MainActivity
import com.capstone.sata.R
import com.capstone.sata.ViewModelFactory
import com.capstone.sata.data.model.ProductResponse
import com.capstone.sata.databinding.ActivityDetailRecomBinding
import com.capstone.sata.utils.Result

class DetailRecomActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailRecomBinding
    private lateinit var recomViewModel: RecomViewModel
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recom)

        binding = ActivityDetailRecomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        factory =  ViewModelFactory.getInstance(this)
        recomViewModel = ViewModelProvider(this, factory)[RecomViewModel::class.java]
        setData()

        val btnMoveActivity: Button = findViewById(R.id.btn_home)
        btnMoveActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_home-> {
                val moveIntent = Intent(this@DetailRecomActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

    private fun setData() {
        val id = intent.getIntExtra(TAG_ID, 0)
        if (id != 0 ){
            recomViewModel.getProductbyId(id).observe(this){
                when(it) {
                    is Result.Success -> {
                        setDetail(it.data )
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }

    private fun setDetail(data: ProductResponse) {
        Glide.with(this)
            .load(data.image).into(binding.ivRecom)
        binding.tvNama.text = data.name
        binding.tvDesc.text = data.description
        binding.tvContact.text = resources.getString(R.string.contact,data.email)
        binding.tvPlace.text = data.location
        binding.tvPrice.text = resources.getString(R.string.harga,data.price.toString())
    }

    companion object{
        const val TAG_ID = "tag_id"
    }



}