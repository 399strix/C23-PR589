package com.capstone.sata.di

import android.content.Context
import com.capstone.sata.data.preferences.UserPreferences
import com.capstone.sata.data.remote.ApiConfig
import com.capstone.sata.data.repo.SataRepo

object Injection {
    fun provideRepository(context: Context): SataRepo{
        val pref = UserPreferences(context)
        val apiService = ApiConfig.hereAppService
        return SataRepo(pref, apiService)
    }
}