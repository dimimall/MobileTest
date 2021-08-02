package gr.dimitra.mobiletest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gr.dimitra.mobiletest.R;
import gr.dimitra.mobiletest.calls.GwfCalls;
import gr.dimitra.mobiletest.calls.NetworkRequest;
import gr.dimitra.mobiletest.calls.RefreshToken;
import gr.dimitra.mobiletest.calls.Tokens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private EditText email;
    private EditText password;
    private Button submit;
    private Retrofit retrofit;
    private GwfCalls apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        retrofit = NetworkRequest.getRetrofitClient();
        apiService = retrofit.create(GwfCalls.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Tokens> call = apiService.UserLogin(email.getText().toString(),password.getText().toString());
                call.enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(Call<Tokens> call, Response<Tokens> response) {
                        Log.d("dimimall","response "+response.body().getAccess());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("refresh", response.body().getRefresh());
                        editor.putString("access", response.body().getAccess());
                        editor.commit();

                        startActivity(new Intent(MainActivity.this, MetersListActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Tokens> call, Throwable t) {
                        Log.d("dimimall","error "+t.getLocalizedMessage());
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            // do something here
            if (!sharedpreferences.getString("refresh","").isEmpty()){

                Call<RefreshToken> call = apiService.refreshToken(sharedpreferences.getString("refresh",""));
                call.enqueue(new Callback<RefreshToken>() {
                    @Override
                    public void onResponse(Call<RefreshToken> call, Response<RefreshToken> response) {
                        Log.d("dimimall","response "+response.body().getAccess());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("access", response.body().getAccess());
                        editor.commit();
                    }

                    @Override
                    public void onFailure(Call<RefreshToken> call, Throwable t) {
                        Log.d("dimimall","error "+t.getLocalizedMessage());
                    }
                });
            }
        }
        else if (id == R.id.logout){
            if (!sharedpreferences.getString("refresh","").isEmpty()) {
                Log.d("dimimall","logout");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("refresh", "");
                editor.putString("access", "");
                editor.commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void init(){
        email = findViewById(R.id.editTextTextEmailAddress);
        email.setText("candidate@gwf-labs.gr");
        password = findViewById(R.id.editTextTextPassword);
        password.setText("candpass");
        submit = findViewById(R.id.button);
    }
}