package com.example.githubassignment.repository.services;

import com.example.githubassignment.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("repos/firebase/firebase-ios-sdk/issues")
    Call<List<Project>> getIssueList();

    @GET("/repos/firebase/firebase-ios-sdk/issues/{commentid}/comments")
    Call<List<Project>> getIssueDetails(@Path("commentid") String commentid);

}
