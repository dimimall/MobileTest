package gr.dimitra.mobiletest.calls;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

public class NetworkRequest {

    public static final String BASE_URL="https://test-api.gwf.ch/";

    public static Retrofit retrofit;

   /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */

    public static Retrofit getRetrofitClient(){

        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit==null) {

            //Defining the Retrofit using Builder
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)   //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                    .build();
        }

        return retrofit;
    }

}
