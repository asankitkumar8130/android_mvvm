package com.example.githubassignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.githubassignment.MainClass;
import com.example.githubassignment.model.Project;
import com.example.githubassignment.repository.services.GitHubService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {
    private static ProjectRepository projectRepository;
    private GitHubService gitHubService;

    private ProjectRepository() {
        //TODO this gitHubService instance will be injected using Dagger in part #2 ...

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         File httpCacheDirectory = new File(MainClass.getInstance().getCacheDir(), "offlineCache");

        //10 MB
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(GitHubService.class);
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Exception e) {

                    CacheControl cacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    Request offlineRequest = chain.request().newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                    return chain.proceed(offlineRequest);
                }
            }
        };
    }
    private Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response originalResponse = chain.proceed(request);
                String cacheControl = originalResponse.header("Cache-Control");

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {


                    CacheControl cc = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    /*return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-stale=" + 60 * 60 * 24)
                            .build();*/


                    request = request.newBuilder()
                            .cacheControl(cc)
                            .build();

                    return chain.proceed(request);

                } else {
                    return originalResponse;
                }
            }
        };

    }


    public synchronized static ProjectRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

//    public MutableLiveData<List<Project>> getProjectList(String userId) {
//        final MutableLiveData<List<Project>> data = new MutableLiveData<>();
//
//        gitHubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
//            @Override
//            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Project>> call, Throwable t) {
//                // TODO better error handling in part #2 ...
//                data.setValue(null);
//            }
//        });
//
//        return data;
//    }


    public MutableLiveData<List<Project>> getIssueList() {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        gitHubService.getIssueList().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
            data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }

//    public MutableLiveData<Project> getProjectDetails(String userID, String projectName) {
//        final MutableLiveData<Project> data = new MutableLiveData<>();
//
//        gitHubService.getProjectDetails(userID, projectName).enqueue(new Callback<Project>() {
//            @Override
//            public void onResponse(Call<Project> call, Response<Project> response) {
//                simulateDelay();
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Project> call, Throwable t) {
//                // TODO better error handling in part #2 ...
//                data.setValue(null);
//            }
//        });
//
//        return data;
//    }

    public MutableLiveData<List<Project>> getIssueDetail(String issueID) {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

        gitHubService.getIssueDetails(issueID).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
