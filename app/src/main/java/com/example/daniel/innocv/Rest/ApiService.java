package com.example.daniel.innocv.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daniel on 12/02/16.
 */
public class ApiService {

    private static ApiInterface service = null;
    private static String baseUrl = "http://hello-world.innocv.com/api/user/";


    public static ApiInterface getInstance(){
        if(service == null){
            new ApiService();
        }
        return service;
    }

    public ApiService(){
        OkHttpClient client = new OkHttpClient();
/*        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        });*/

        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(ApiResponse.class, new ApiDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        service = retrofit.create(ApiInterface.class);

    }


}
