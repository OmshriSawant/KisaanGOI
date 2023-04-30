package com.example.kisaangoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "KisaanGOI Filter Activity";
    private GoiAdapter goiAdapter;
    private RecyclerView recyclerView;
    private ArrayList<GoiDetail> goiDetails;
    private ProgressBar progressBar2;
    private EditText searchText;

    private Boolean isScrolling = false;
    int offset = 20;
    int currentItems, totalItems, scrolledItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        FilterActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.BarLayout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kisaan GOI");

        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        goiDetails = new ArrayList<>();
        recyclerView = findViewById(R.id.filterRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        goiAdapter = new GoiAdapter(this, goiDetails);
        recyclerView.setAdapter(goiAdapter);

        searchText = findViewById(R.id.searchByDistrict);
        searchText.requestFocus();

        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            progressBar2.setVisibility(View.VISIBLE);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&offset=0&limit=5";

            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    response -> {
                        Log.d(FilterActivity.TAG, response.toString());
                        goiDetails.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String records = "";
                            records = jsonObject.getString("records");
                            JSONArray arr = new JSONArray(records);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject part = arr.getJSONObject(i);
                                GoiDetail details = new GoiDetail();
                                details.setGroceryName(part.getString("commodity"));
                                details.setGroceryPlace(part.getString("district") + ", " + part.getString("state"));
                                details.setGroceryPrice(part.getString("modal_price"));
                                goiDetails.add(details);
                            }
                            recyclerView.swapAdapter(new GoiAdapter(this, goiDetails), true);
                            progressBar2.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Log.e(FilterActivity.TAG, error.toString())
            );
            queue.add(stringRequest);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.mainmenu,menu);
            return true;
            }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            super.onOptionsItemSelected(item);

            if(item.getItemId()==R.id.sortByPrice){
            if(goiDetails.isEmpty()) {
            Toast.makeText(FilterActivity.this,"No data for sorting", Toast.LENGTH_SHORT).show();

            }
            else {
                goiDetails.sort(Comparator.comparing(g -> Integer.valueOf(g.getGroceryPrice())));
                goiAdapter.notifyDataSetChanged();
            }
            }
            else if (item.getItemId() == R.id.sortByDate) {
                if(goiDetails.isEmpty()) {
                 Toast.makeText(FilterActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }
            else if(item.getItemId()== android.R.id.home) {
            finish();
            }
            return true;
            }
}