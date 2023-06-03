package com.capstone.sata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sata.R
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.databinding.ItemFilterBinding
import com.capstone.sata.ui.fragment.QuestionFragment

class FilterAdapter(private val list: List<DataFilter>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    class ViewHolder(private val item:ItemFilterBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(data: DataFilter){
            Glide.with(itemView.context)
                .load(data.imgUrl).into(item.ivFilter)
            item.tvFilter.text = data.tvDesc

            itemView.rootView.setOnClickListener {
                val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                val questionFragment = QuestionFragment()
                fragmentManager.beginTransaction().apply {
                    replace(R.id.container, questionFragment)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }
}