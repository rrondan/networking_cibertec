package com.rrondan.clasenetworking.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rrondan.clasenetworking.R;
import com.rrondan.clasenetworking.adapters.PostAdapter;
import com.rrondan.clasenetworking.models.Post;
import com.rrondan.clasenetworking.networking.ClientApi;
import com.rrondan.clasenetworking.networking.RetrofitUtil;
import com.rrondan.clasenetworking.utils.OnAdapterItemClick;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RecyclerView recyclerViewPosts;
    private FloatingActionButton floatingActionButton;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private final static int CREATE_POST_RESULT = 1;
    private final static int EDIT_POST_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textViewResult);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivityForResult(intent, CREATE_POST_RESULT);
            }
        });
        setRecyclerView();
        callService();
    }

    private void setRecyclerView(){
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, new OnAdapterItemClick() {
            @Override
            public void onClick(int position) {
                Post post = postList.get(position);
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                intent.putExtra("post", Parcels.wrap(post));
                startActivityForResult(intent, EDIT_POST_RESULT);
            }
        });
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPosts.setAdapter(postAdapter);
    }

    private void callService() {
        Call<List<Post>> call = RetrofitUtil.getClientApi().getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                } else {
                    List<Post> posts = response.body();
                    postList.addAll(posts);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CREATE_POST_RESULT:
                if(resultCode == Activity.RESULT_OK && data != null){
                    Post postResult = Parcels.unwrap(data.getParcelableExtra("post"));
                    postList.add(0, postResult);
                    postAdapter.notifyDataSetChanged();
                }
                break;
            case EDIT_POST_RESULT:
                if(resultCode == Activity.RESULT_OK && data != null){
                    Post postResult = Parcels.unwrap(data.getParcelableExtra("post"));
                    //postList.add(0, postResult);
                    for(int i = 0; i < postList.size(); i++){
                        if(postList.get(i).getId() == postResult.getId()){
                            postList.set(i,postResult);
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                }

                break;
        }
    }
}