package com.capstone.sata.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.sata.R
import com.capstone.sata.data.model.DataQuestion
import com.capstone.sata.dummy.DataDummy.dataQuestion

class QuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()
    }

    private fun showRecyclerView() {
        val data = dataQuestion
    }

    fun checkDataQuestion(dataQuestion: DataQuestion?){
        if (dataQuestion == null){
            return
        }
    }
}