package movies.com.androidmovieapp.service;
import movies.com.androidmovieapp.util.Properties;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieService {
    private Retrofit retrofit = null;


    public MovieDbApi getAPI() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Properties.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MovieDbApi.class);
    }
}
