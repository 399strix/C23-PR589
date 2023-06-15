package com.capstone.sata

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sata.data.repo.SataRepo
import com.capstone.sata.di.Injection
import com.capstone.sata.ui.cam.CameraViewModel
import com.capstone.sata.ui.filter.FilterViewModel
import com.capstone.sata.ui.recom.RecomViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repo: SataRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repo) as T
            }
            modelClass.isAssignableFrom(FilterViewModel::class.java) -> {
                FilterViewModel(repo) as T
            }
            modelClass.isAssignableFrom(RecomViewModel::class.java) -> {
                RecomViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}