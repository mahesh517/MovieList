package app.com.movielist.ApiClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIServiceFactory {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";


    public static Retrofit getRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Customize the request
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
//                    .header("Authorization", "auth-token")
                    .method(original.method(), original.body())
                    .build();

            Response response = chain.proceed(request);

            return response;
        });

        OkHttpClient OkHttpClient = httpClient.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();

        return retrofit;
    }





}

