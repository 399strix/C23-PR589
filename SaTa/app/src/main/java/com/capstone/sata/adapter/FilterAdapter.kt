package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ItemFilterBinding

class FilterAdapter(private val list: List<DataFilter>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }
    class ViewHolder(private val item:ItemFilterBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: DataFilter){
            Glide.with(itemView.context)
                .load(data.imgUrl).into(item.ivFilter)
            item.tvFilter.text = data.tvDesc

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        val userPreferences = UserPreferences(holder.itemView.context)
        userPreferences.setBudget(position)
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataFilter)
    }
}