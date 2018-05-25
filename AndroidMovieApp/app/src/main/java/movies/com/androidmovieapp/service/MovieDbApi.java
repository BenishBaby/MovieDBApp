package movies.com.androidmovieapp.service;


import movies.com.androidmovieapp.Model.CollectionDetails;
import movies.com.androidmovieapp.Model.MovieDetails;
import movies.com.androidmovieapp.Model.NowPlayingMoviesDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieDbApi {

    @GET("movie/now_playing")
    Call<NowPlayingMoviesDetails> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("collection/{collection_id}")
    Call<CollectionDetails> getMovieCollection(@Path("collection_id") String collectionId, @Query("api_key") String apiKey);
}
