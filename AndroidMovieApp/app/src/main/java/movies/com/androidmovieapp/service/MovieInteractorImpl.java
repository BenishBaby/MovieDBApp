package movies.com.androidmovieapp.service;

import movies.com.androidmovieapp.Model.CollectionDetails;
import movies.com.androidmovieapp.Model.MovieDetails;
import movies.com.androidmovieapp.Model.NowPlayingMoviesDetails;
import movies.com.androidmovieapp.Model.NowPlayingMoviesResults;
import movies.com.androidmovieapp.util.Properties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieInteractorImpl implements MovieInteractor {

    private MovieService movieService;

    public MovieInteractorImpl(MovieService movieService) {
        if (this.movieService == null) {
            this.movieService = new MovieService();
        }
    }

    @Override
    public void fetchNowPlayingMovies( final NowPlayingMoviesAPIFinishedListener listener) {

        movieService
                .getAPI()
                .getNowPlayingMovies(Properties.MOVIE_DB_API_KEY)
                .enqueue(new Callback<NowPlayingMoviesDetails>() {
                    @Override
                    public void onResponse(Call<NowPlayingMoviesDetails> call, Response<NowPlayingMoviesDetails> response) {

                        if(response != null) {
                            NowPlayingMoviesDetails data = response.body();
                            if (data != null ) {
                                NowPlayingMoviesResults[] results = data.getResults();
                                listener.onSuccess(results);
                            }
                        } else {
                            listener.onNowPlayingMoviesAPIFetchError();
                        }
                    }

                    @Override
                    public void onFailure(Call<NowPlayingMoviesDetails> call, Throwable t) {

                            listener.onNowPlayingMoviesAPIFetchError();
                    }
                });

    }

    @Override
    public void fetchMovieDetails(final MovieDetailsAPIFinishedListener listener,String movieId){

        movieService
                .getAPI()
                .getMovieDetails(movieId,Properties.MOVIE_DB_API_KEY)
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                        if(response != null) {
                            MovieDetails data = response.body();
                            if (data != null ) {
                                listener.onSuccess(data);
                            }
                        } else {
                            listener.onMovieDetailAPIFetchError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                        listener.onMovieDetailAPIFetchError();
                    }
                });
    }

    @Override
    public void fetchMovieCollection(final MovieCollectionsAPIFinishedListener listener,String collectionId){

        movieService
                .getAPI()
                .getMovieCollection(collectionId,Properties.MOVIE_DB_API_KEY)
                .enqueue(new Callback<CollectionDetails>() {
                    @Override
                    public void onResponse(Call<CollectionDetails> call, Response<CollectionDetails> response) {

                        if(response != null) {
                            CollectionDetails data = response.body();
                            if (data != null ) {

                                listener.onSuccess(data);
                            }
                        } else {
                            listener.onMovieCollectionAPIFetchError();
                        }
                    }

                    @Override
                    public void onFailure(Call<CollectionDetails> call, Throwable t) {

                        listener.onMovieCollectionAPIFetchError();
                    }
                });
    }
}
