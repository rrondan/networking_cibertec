package com.rrondan.clasenetworking.networking;

import com.rrondan.clasenetworking.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(
            @Field("title") String title,
            @Field("body") String body,
            @Field("userId") long userId
    );

    @PUT("/posts/{id}")
    @FormUrlEncoded
    Call<Post> updatePost(
            @Field("title") String title,
            @Field("body") String body,
            @Field("userId") long userId,
            @Field("id") long id,
            @Path("id") long idPost
    );
}
