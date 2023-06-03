package com.capstone.sata.data.preferences

import android.content.Context
import com.capstone.sata.data.model.DataPreferences

class UserPreferences(context : Context){
    companion object{
        private const val JENIS_WISATA = "jenis_wisata"
        private const val BUDGET = "0"
        private const val DAERAH = "daerah"
    }

    private val preferences = context.getSharedPreferences(JENIS_WISATA, Context.MODE_PRIVATE)

    fun setJenisWisata(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(JENIS_WISATA, value)
        editor.apply()
    }

    fun setBudget(value: Int) {
        val editor = preferences.edit()
        editor.putInt(BUDGET, value)
        editor.apply()
    }

    fun setDaerah(value: String) {
        val editor = preferences.edit()
        editor.putString(DAERAH, value)
        editor.apply()
    }

    fun getUser(): DataPreferences {
        val model = DataPreferences()
        model.jenisWisata = preferences.getBoolean(JENIS_WISATA, true)
        model.budget = preferences.getInt(BUDGET, 0)
        model.daerah = preferences.getString(DAERAH, "")
        return model
    }
}