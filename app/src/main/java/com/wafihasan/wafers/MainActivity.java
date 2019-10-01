package com.wafihasan.wafers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public RecyclerView recyclerView;
    public Adapter adapter;
    public ArrayList<Items> itemsArrayList;
    public RequestQueue requestQueue;
    public static final String TAG = "Tag";
    public static final String BASE_URL = "https://pixabay.com/api/?key=13664094-f5ac3ff9c5a471443cfc5053a";
    public String imageUrl;
    public String hiresUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    public void parseJSON()
    {
        String url = "https://pixabay.com/api/?key=13664094-f5ac3ff9c5a471443cfc5053a&q=red&image_type=all&per_page=200&orientation=all";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject hits = jsonArray.getJSONObject(i);
                        imageUrl = hits.getString("webformatURL");
                        hiresUrl = hits.getString("fullHDURL");

                        itemsArrayList.add(new Items(imageUrl, hiresUrl));

                    }
                    adapter = new Adapter(MainActivity.this, itemsArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });
        request.setTag(TAG);
        requestQueue.add(request);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        cancelVolley();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        cancelVolley();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cancelVolley();
    }

    @Override
    public void onItemClick(int position)
    {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        Items clickedItem = itemsArrayList.get(position);
        detailsIntent.putExtra("fullHDURL", clickedItem.getHiresUrl());
        startActivity(detailsIntent);
        cancelVolley();
    }

    public void cancelVolley()
    {
        requestQueue.cancelAll(new RequestQueue.RequestFilter()
        {
            @Override
            public boolean apply(Request<?> request)
            {
                return true;
            }
        });
        requestQueue.cancelAll(TAG);
    }
}
