package com.example.android.simpleloginvolley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hesham on 12/3/2017.
 */

public class BackgroundTask {

    private Context mContext;
    ArrayList<Contact> arrayList;
    private String serverUrl = "http://dev.alsokary.com:8005/api/glocodata";

    public BackgroundTask (Context context){
        mContext = context;
    }

    public ArrayList<Contact> getList(){
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
                                arrayList.add(contact);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(mContext, "Greate....", Toast.LENGTH_LONG).show();
                        Log.d("Response ResponseResponseResponse Lenght", String.valueOf(arrayList.size()));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error....", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });


        MySingleton.getInstance(mContext).addToRequestQeue(jsonArrayRequest);

        return arrayList;
    }
}
