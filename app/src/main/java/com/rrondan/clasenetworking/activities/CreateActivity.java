package com.rrondan.clasenetworking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rrondan.clasenetworking.R;
import com.rrondan.clasenetworking.models.Post;
import com.rrondan.clasenetworking.networking.ClientApi;
import com.rrondan.clasenetworking.networking.RetrofitUtil;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity
    implements View.OnClickListener {

    private EditText editTextTitle;
    private EditText editTextBody;
    private Button buttonSave;
    private static String TAG = CreateActivity.class.getSimpleName();
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra("post")){
            post = Parcels.unwrap(intent.getParcelableExtra("post"));
            editTextTitle.setText(post.getTitle());
            editTextBody.setText(post.getText());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSave:
                String title = editTextTitle.getText().toString();
                String body = editTextBody.getText().toString();
                if(!title.isEmpty() && !body.isEmpty()){
                    if(post != null){
                        updatePost(title, body, post);
                    } else {
                        savePost(title, body);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Llene ambos campos",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void savePost(String title, String body){
        RetrofitUtil.getClientApi().savePost(title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CreateActivity.this, "Post Creado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Post subido correctamente: " + response.body().toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("post", Parcels.wrap(response.body()));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Ha ocurrio un error: " + t.getMessage());
            }
        });
    }

    private void updatePost(String title, String body, Post post){
        RetrofitUtil.getClientApi().updatePost(title, body, post.getUserId(), post.getId(), post.getId())
                .enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CreateActivity.this, "Post Actualizado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Post actualizado correctamente: " + response.body().toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("post", Parcels.wrap(response.body()));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Ha ocurrio un error: " + t.getMessage());
            }
        });
    }
}