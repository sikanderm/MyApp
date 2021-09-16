package com.example.myapp;
import java.util.*;
import org.json.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi jsonApi = retrofit.create(jsonApi.class);
        Call<List<Post>> call = jsonApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                List<Post> posts1 = new ArrayList<Post>();
                List<Post> sortedPosts = new ArrayList<Post>();
                for (int i = 1; i < posts.size(); i++){
                    for (Post post : posts){
                        if (post.getName() != null){
                            if (!post.getName().isEmpty()) {
                                if (post.getListId() == i) {
                                    posts1.add(post);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < 9999; i++){
                    for (Post post : posts1){
                        String s = post.getName();
                        String[] arr = s.split(" ");
                        if (Integer.valueOf(arr[1]) == i) {
                            sortedPosts.add(post);
                        }
                    }
                }
                for (Post post : sortedPosts) {
                    String content = "";
                    content += "id: " + post.getId() + "\n";
                    content += "listId: " + post.getListId() + "\n";
                    content += "Name: " + post.getName() + "\n" +"\n";
                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}