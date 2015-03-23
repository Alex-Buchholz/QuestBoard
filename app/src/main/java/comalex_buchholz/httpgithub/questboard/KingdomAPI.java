package comalex_buchholz.httpgithub.questboard;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Alex Buchholz on 3/16/2015.
 * <p/>
 * KingdomAPI Interface - Retrofit commands for posting or receiving data from web based data sets.
 */
public interface KingdomAPI {

    //Get kingdom information.
    @GET("/api/v1/kingdoms")
    public void getKingdoms(Callback<List<KingdomInformation>> response);

    //Get specific kingdom information with their related quests.
    @GET("/api/v1/kingdoms/{id}")
    public void getKingdomDetails(@Path("id") int kingdomId,
                                  Callback<AreaInformation> response);

    //Post the Email of the user to the web service.
    @POST("/api/v1/subscribe")
    public void postEmail(@Body User user, Callback<UserResponse> response);
}