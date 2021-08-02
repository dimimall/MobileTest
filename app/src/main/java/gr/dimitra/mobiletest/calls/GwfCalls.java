package gr.dimitra.mobiletest.calls;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GwfCalls {

    @FormUrlEncoded
    @POST("auth/token/")
    Call<Tokens> UserLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/token/refresh/")
    Call<RefreshToken> refreshToken(@Field("refresh") String refresh);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/reports/measurements/")
    Call<List<Meters>> getAllMeters(@Header("Authorization") String auth);

}
