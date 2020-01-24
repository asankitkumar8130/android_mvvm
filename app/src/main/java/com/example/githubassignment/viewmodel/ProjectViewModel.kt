package com.example.githubassignment.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubassignment.model.Project
import com.example.githubassignment.repository.ProjectRepository

class ProjectViewModel(application: Application, projectId: String) :
    AndroidViewModel(application) {
    var projectObservable = MutableLiveData<List<Project>>()
    var projectID:String= projectId;
    var project= ObservableField<Project>()

    init {
        projectObservable= ProjectRepository.getInstance().getIssueDetail(projectId)
    }
    fun  getdata(): MutableLiveData<List<Project>> {
        return projectObservable
    }

    fun setdata(project: Project){
         this.project.set(project)
    }

}