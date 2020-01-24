package com.ecommerce.myapplication.example3.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubassignment.viewmodel.ProjectViewModel

class DetailProjectFactory(val application: Application,val projectId :String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(application, projectId) as T
    }
}
