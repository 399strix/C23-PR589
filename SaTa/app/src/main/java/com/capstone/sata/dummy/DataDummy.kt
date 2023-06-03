package com.capstone.sata.dummy

import com.capstone.sata.data.model.Answer
import com.capstone.sata.data.model.DataFilter
import com.capstone.sata.data.model.DataQuestion

object DataDummy {
    fun generateDataDummy(): List<DataFilter> {
        val list = ArrayList<DataFilter>()

        for (i in 0..5) {
            val data = DataFilter(
                "https://cdn-icons-png.flaticon.com/512/4135/4135890.png",
                "Pegunungan"
            )
            list.add(data)
        }
        return list
    }

    fun listAnswer(): List<Answer> {
        val list = ArrayList<Answer>()

        for (i in 0..25) {
            list.add(
                Answer(
                    "Ponorogo",
                )
            )
        }
        return list
    }


}