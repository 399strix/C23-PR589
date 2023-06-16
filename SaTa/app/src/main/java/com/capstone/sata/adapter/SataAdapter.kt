package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sata.data.model.Answer
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ItemQuestionBinding

class SataAdapter(private val list: List<String>) : RecyclerView.Adapter<SataAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val item: ItemQuestionBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: String){
            item.answer.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
       holder.itemView.setOnClickListener {
           this.onItemClickCallback.onItemClicked(data, position)
       }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String, position: Int)
    }
}