package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sata.data.model.Answer
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.databinding.ItemQuestionBinding

class FragmentAdapter(private val list: List<Answer>) : RecyclerView.Adapter<FragmentAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val item: ItemQuestionBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: Answer){
            item.answer.text=data.answer
            val userPreferences = UserPreferences(itemView.context)
            userPreferences.setDaerah(data.answer)

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
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Answer)
    }
}