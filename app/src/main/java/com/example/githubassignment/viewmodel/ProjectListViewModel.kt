package com.example.githubassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubassignment.model.Project
import com.example.githubassignment.repository.ProjectRepository

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {
     var projectListObservable = MutableLiveData<List<Project>>()

    init {
        projectListObservable= ProjectRepository.getInstance().issueList
    }
    fun  getdata():MutableLiveData<List<Project>>{
        return projectListObservable
    }
}
