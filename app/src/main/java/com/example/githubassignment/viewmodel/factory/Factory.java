package com.example.githubassignment.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubassignment.viewmodel.ProjectViewModel;

public  class Factory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;

    private final String projectID;

    public Factory(@NonNull Application application, String projectID) {
        this.application = application;
        this.projectID = projectID;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProjectViewModel(application, projectID);
    }
}
