package com.rrondan.clasenetworking.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static Retrofit retrofit;
    private static ClientApi clientApi;

    public static Retrofit getRetrofit(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ClientApi getClientApi(){
        if(clientApi == null){
            clientApi = getRetrofit().create(ClientApi.class);
        }
        return clientApi;
    }
}
