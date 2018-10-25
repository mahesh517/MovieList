package app.com.movielist.Activities;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.com.movielist.BaseClass.BaseActivity;
import app.com.movielist.ModelClass.MovieDetailsApi.Genre;
import app.com.movielist.ModelClass.MovieDetailsApi.MovieDetails;
import app.com.movielist.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.com.movielist.ApiClass.APIServiceFactory.IMAGE_URL;

public class MovieDetailsActivity extends BaseActivity {

    String movie_id;

    ImageView banner;
    TextView runtime, date, rating, category, lang, budget, revenue, over_view;

    private DateFormat server_format = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat deisgn_format = new SimpleDateFormat("dd MMM yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_vie_details);

        movie_id = getIntent().getStringExtra("movie_id");

        Log.e("movie_id", movie_id);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        init();


    }

    private void init() {

        banner = findViewById(R.id.banner_iv);
        runtime = findViewById(R.id.runtime);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        category = findViewById(R.id.category);
        lang = findViewById(R.id.language);
        budget = findViewById(R.id.budget);
        revenue = findViewById(R.id.revenue);
        over_view = findViewById(R.id.over_view);

        getMovieDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMovieDetails() {
        try {
            show_progress();
            apiService.getMovieDetails(movie_id, API_KEY, "en_us").enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                    dismiss_progress();

                    Log.e("response", response.body().getOverview());
                    Log.e("min", String.valueOf(response.body().getRuntime()));
                    Date new_date = null;
                    if (response.raw().code() == 200) {

                        try {
                            new_date = server_format.parse(response.body().getReleaseDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        date.setText(deisgn_format.format(new_date));
                        rating.setText("" + response.body().getVoteAverage());
                        lang.setText(response.body().getOriginalLanguage());
                        budget.setText("$" + " " + response.body().getBudget());
                        revenue.setText("$" + " " + response.body().getRevenue());

                        getSupportActionBar().setTitle(response.body().getTitle());
                        String url = IMAGE_URL + response.body().getBackdropPath();
                        loadbanner(url);
                        over_view.setText(response.body().getOverview());
                        runtime.setText(response.body().getRuntime() + " Mins");
                        List<Genre> genres = response.body().getGenres();

                        for (int i = 0; i < genres.size(); i++) {

                            category.append(genres.get(i).getName() + ",");
                        }
                    }


                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            dismiss_progress();
            show_Toast(e.getMessage());
        }

    }

    private void loadbanner(String url) {
        Glide.with(MovieDetailsActivity.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        banner.setVisibility(View.VISIBLE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        banner.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(banner);

    }


}
