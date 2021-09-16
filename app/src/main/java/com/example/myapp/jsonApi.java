package com.example.myapp;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface jsonApi {
    @GET("hiring.json")
    Call<List<Post>> getPosts();
}
