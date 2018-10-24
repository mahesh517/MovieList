package app.com.movielist.Adpters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import app.com.movielist.ModelClass.MovieListApi.Result;
import app.com.movielist.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {


    private List<Result> resultList;
    private Context context;
    MovieListInterface movieListInterface;
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    private DateFormat server_format = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat deisgn_format = new SimpleDateFormat("dd MMM yyyy");

    public MovieAdapter(List<Result> resultList, Context context, MovieListInterface movieListInterface) {
        this.resultList = resultList;
        this.context = context;
        this.movieListInterface = movieListInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.movie_name.setText(resultList.get(i).getOriginalTitle());
        holder.movie_desc.setText(resultList.get(i).getOverview());
        holder.movie_rating.setText("" + resultList.get(i).getVoteAverage());
        holder.movie_lang.setText(resultList.get(i).getOriginalLanguage());
        String url = IMAGE_URL + resultList.get(i).getPosterPath();
//        Glide.with(context)
//                .load(url)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
//                        holder.movie_poster_iv.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.GONE);
//                        holder.movie_poster_iv.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//                })
//                .error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.mipmap.ic_launcher).into(holder.movie_poster_iv);

        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.movie_poster_iv.setVisibility(View.VISIBLE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.movie_poster_iv.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(holder.movie_poster_iv);
        Date new_date = null;
        try {
            new_date = server_format.parse(resultList.get(i).getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.movie_date.setText(deisgn_format.format(new_date));

        holder.view_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieListInterface != null) {
                    movieListInterface.onMovieSelected(String.valueOf(resultList.get(i).getId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView movie_name, movie_desc, movie_rating, movie_lang, movie_date;
        ImageView movie_poster_iv, view_iv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.loader);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_desc = itemView.findViewById(R.id.movie_desc);
            movie_date = itemView.findViewById(R.id.date);
            movie_lang = itemView.findViewById(R.id.language);
            movie_rating = itemView.findViewById(R.id.rating);
            movie_poster_iv = itemView.findViewById(R.id.movie_poster);
            view_iv = itemView.findViewById(R.id.view_icon);
        }
    }

    public interface MovieListInterface {
        void onMovieSelected(String id);
    }
}
