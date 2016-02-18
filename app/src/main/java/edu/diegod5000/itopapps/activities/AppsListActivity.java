package edu.diegod5000.itopapps.avtivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.diegod5000.itopapps.R;
import edu.diegod5000.itopapps.adapters.AppsRecyclerViewAdapter;
import edu.diegod5000.itopapps.fragments.AppDetailFragment;
import edu.diegod5000.itopapps.services.HttpRequester;
import edu.diegod5000.itopapps.services.models.App;

public class AppsListActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private AppsRecyclerViewAdapter appsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.isTablet))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_apps_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        fetchAppsData();

        RecyclerView recyclerViewApps = (RecyclerView) findViewById(R.id.recyclerViewApps);
        assert recyclerViewApps != null;
        setupRecyclerView(recyclerViewApps);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        if (getResources().getBoolean(R.bool.isTablet)){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        appsRecyclerViewAdapter = new AppsRecyclerViewAdapter(new ArrayList<App>());
        appsRecyclerViewAdapter.setOnItemTapListener(new AppsRecyclerViewAdapter.OnItemTapListener() {
            @Override
            public void onTap(App app) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(AppDetailFragment.ARG_ITEM_APP, app);
                    AppDetailFragment fragment = new AppDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(AppsListActivity.this, AppDetailActivity.class);
                    intent.putExtra(AppDetailFragment.ARG_ITEM_APP, app);

                    AppsListActivity.this.startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(appsRecyclerViewAdapter);
    }

    private void fetchAppsData(){
        final String BASE_URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
        JsonObjectRequest jsonAppsRequest = new JsonObjectRequest
                (Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        saveDataInCache(response.toString());
                        ArrayList<App> apps = new ArrayList<>();
                        JSONArray entries = response.optJSONObject("feed").optJSONArray("entry");
                        for (int i = 0; i<entries.length(); i++){
                            App app = new App(entries.optJSONObject(i));
                            apps.add(app);
                        }

                        appsRecyclerViewAdapter.swapData(apps);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ArrayList<App> apps = new ArrayList<>();
                        try {
                            JSONObject response = new JSONObject(getCacheData());
                            JSONArray entries = response.optJSONObject("feed").optJSONArray("entry");
                            for (int i = 0; i<entries.length(); i++){
                                App app = new App(entries.optJSONObject(i));
                                apps.add(app);
                            }

                            appsRecyclerViewAdapter.swapData(apps);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(AppsListActivity.this, "Verifique su conexión a Internet", Toast.LENGTH_LONG).show();
                    }
                });

        HttpRequester.getInstance(this).addToRequestQueue(jsonAppsRequest);
    }

    public void saveDataInCache(String json){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.key_sharedpreferences), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.key_json_cache, ""), json);
    }

    public String getCacheData(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.key_sharedpreferences), 0);
        return sharedPreferences.getString(getString(R.string.key_json_cache), "");
    }

}
