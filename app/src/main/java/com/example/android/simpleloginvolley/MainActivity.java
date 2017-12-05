package com.example.android.simpleloginvolley;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contact> mArrayList = new ArrayList<>() ;
    private String serverUrl = "http://dev.alsokary.com:8005/api/glocodata";
    private Button addDataButton;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDataButton = (Button) findViewById(R.id.bn_add_data) ;
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddReadingActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, serverUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()){
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Contact contact = new Contact(jsonObject.getInt("id"),
                                        jsonObject.getString("meter_id"),
                                        jsonObject.getInt("value"),
                                        jsonObject.getString("timestamp"));
                                mArrayList.add(contact);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter = new RecyclerAdapter(mArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error....", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQeue(jsonArrayRequest);




    }
}
