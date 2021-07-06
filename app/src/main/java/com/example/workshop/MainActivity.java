package com.example.workshop;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main class represent all workshop items.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<WorkshopItem> workshopItemList;
    WorkshopAdapter adapter;

    String  mQueryString;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        workshopItemList=new ArrayList<>();
        fetchWorkshops();

    }


    /**
     * Parsing data from json.
     */
    private void fetchWorkshops() {
        String url="https://bigvu-interviews-assets.s3.amazonaws.com/workshops.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        String description = jsonObject.getString("description");
                        String text = jsonObject.getString("text");
                        String video = jsonObject.getString("video");

                        WorkshopItem workshopItem= new WorkshopItem(name,image,description,text,video);
                        workshopItemList.add(workshopItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Collections.sort(workshopItemList);
                    adapter = new WorkshopAdapter(MainActivity.this,workshopItemList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Search action override method;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQueryString = newText;
                mHandler.removeCallbacksAndMessages(null);

                //Debounce 300ms between showing results
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getFilter().filter(newText);
                    }
                }, 300);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}