package data;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {

    @POST("add_location.php")
    Call<Void> addLocation(@Body LocationData location);

}