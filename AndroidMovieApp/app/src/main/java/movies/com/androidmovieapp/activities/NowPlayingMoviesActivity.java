package movies.com.androidmovieapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import butterknife.Unbinder;
import movies.com.androidmovieapp.Model.NowPlayingMoviesResults;
import movies.com.androidmovieapp.R;
import movies.com.androidmovieapp.adapter.MovieItem;
import movies.com.androidmovieapp.adapter.MovieGridAdapter;
import movies.com.androidmovieapp.presenter.NowPlayingMoviePresenter;
import movies.com.androidmovieapp.presenter.NowPlayingMoviePresenterImpl;
import movies.com.androidmovieapp.service.MovieService;
import movies.com.androidmovieapp.service.MovieInteractorImpl;
import movies.com.androidmovieapp.util.Properties;
import movies.com.androidmovieapp.view.NowPlayingMovieView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingMoviesActivity extends AppCompatActivity implements NowPlayingMovieView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.gridView)
    GridView gridView;

    private MovieGridAdapter adapter;

    private NowPlayingMoviePresenter presenter;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplayingmovies);
        unbinder = ButterKnife.bind(this);
        presenter = new NowPlayingMoviePresenterImpl(this,new MovieInteractorImpl(new MovieService()));
        presenter.fetchNowPlayingMovies();
        adapter = new MovieGridAdapter(this, R.layout.nowplaying_movie_grid_item, new ArrayList<MovieItem>(),presenter);
        gridView.setAdapter(adapter);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMovieFetchError() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.api_error_alert_message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void setNowPlayingMovies(NowPlayingMoviesResults[] results) {

        progressBar.setVisibility(View.GONE);
        adapter.clear();

        for(NowPlayingMoviesResults result : results) {

            MovieItem item = new MovieItem();
            item.setmUrl(Properties.BASE_IMAGE_URL  + result.getPoster_path());
            item.setmTitle(result.getTitle());
            item.setMovieId(result.getId());
            adapter.add(item);
        }

    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void naviageToDetailPage(String movieId) {

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);

    }
}
