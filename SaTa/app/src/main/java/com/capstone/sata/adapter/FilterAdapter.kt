package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.databinding.ItemFilterBinding

class FilterAdapter(private val list: List<DataFilter>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    class ViewHolder(private val item:ItemFilterBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: DataFilter){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}