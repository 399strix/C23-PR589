package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sata.data.model.Answer
import com.capstone.sata.databinding.ItemQuestionBinding

class FragmentAdapter(private val list: List<Answer>) : RecyclerView.Adapter<FragmentAdapter.ViewHolder>() {
    class ViewHolder(private val item: ItemQuestionBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: Answer){
            item.answer.text=data.answer
            itemView.rootView.setOnClickListener {

            }
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
    }
}