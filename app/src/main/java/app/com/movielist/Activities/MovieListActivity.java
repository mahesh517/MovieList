package app.com.movielist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import app.com.movielist.Adpters.MovieAdapter;
import app.com.movielist.BaseClass.BaseActivity;
import app.com.movielist.ModelClass.MovieListApi.MovieList;
import app.com.movielist.ModelClass.MovieListApi.Result;
import app.com.movielist.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends BaseActivity {

    RecyclerView movie_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        init();


    }

    private void init() {

        movie_view = findViewById(R.id.movie_view);
        movie_view.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));
        movie_view.setHasFixedSize(true);

        getMovieList();


    }

    private void getMovieList() {
        try {

            show_progress();
            apiService.getMoviesList(API_KEY, "en-US", "popularity.desc", "false", "false", "1").enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                    dismiss_progress();

                    if (response.raw().code() == 200) {
                        List<Result> resultList = response.body().getResults();

                        if (resultList.size() > 0) {

                            movie_view.setAdapter(new MovieAdapter(resultList, MovieListActivity.this, new MovieAdapter.MovieListInterface() {
                                @Override
                                public void onMovieSelected(String id) {
                                    startActivity(new Intent(MovieListActivity.this, MovieDetailsActivity.class).putExtra("movie_id",id));
                                }
                            }));
                        }
                    }

                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    dismiss_progress();

                    show_Toast(t.getMessage());
                }
            });


        } catch (Exception e) {
            dismiss_progress();
            Log.e("Exception", e.getMessage());
        }
    }
}
