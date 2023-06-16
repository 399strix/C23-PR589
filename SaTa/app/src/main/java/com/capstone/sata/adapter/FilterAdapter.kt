package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sata.data.model.RecommendationsItem
import com.capstone.sata.databinding.ItemQuestionBinding

class FilterAdapter (private val list: List<RecommendationsItem>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val item: ItemQuestionBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: RecommendationsItem){
            item.answer.text = data.name
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
            this.onItemClickCallback.onItemClicked(data)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: RecommendationsItem)
    }
}