package gr.dimitra.mobiletest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import java.util.List;

import gr.dimitra.mobiletest.adapters.MetersAdapter;
import gr.dimitra.mobiletest.R;
import gr.dimitra.mobiletest.calls.GwfCalls;
import gr.dimitra.mobiletest.calls.Meters;
import gr.dimitra.mobiletest.calls.NetworkRequest;
import gr.dimitra.mobiletest.calls.RefreshToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MetersListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static final String MyPREFERENCES = "MyPrefs" ;
    private SharedPreferences sharedpreferences;
    private Retrofit retrofit;
    private GwfCalls apiService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MetersAdapter metersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meters_list);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        retrofit = NetworkRequest.getRetrofitClient();
        apiService = retrofit.create(GwfCalls.class);

        // Getting reference of swipeRefreshLayout and recyclerView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting the layout as Linear for vertical orientation to have swipe behavior
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<List<Meters>> calls = apiService.getAllMeters("Bearer "+sharedpreferences.getString("access",""));
        calls.enqueue(new Callback<List<Meters>>() {
            @Override
            public void onResponse(Call<List<Meters>> call, Response<List<Meters>> response) {
                metersAdapter = new MetersAdapter(MetersListActivity.this,response.body());
                recyclerView.setAdapter(metersAdapter);
            }

            @Override
            public void onFailure(Call<List<Meters>> call, Throwable t) {
                Log.d("dimimall",t.getLocalizedMessage());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

                Call<List<Meters>> calls = apiService.getAllMeters("Bearer "+sharedpreferences.getString("access",""));
                calls.enqueue(new Callback<List<Meters>>() {
                    @Override
                    public void onResponse(Call<List<Meters>> call, Response<List<Meters>> response) {
                        metersAdapter = new MetersAdapter(MetersListActivity.this,response.body());
                        recyclerView.setAdapter(metersAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Meters>> call, Throwable t) {
                        Log.d("dimimall",t.getLocalizedMessage());
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu_list_item, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(metersAdapter==null){
        }else {
            String text = s;
            metersAdapter.filter(text);
        }
        return false;
    }
}