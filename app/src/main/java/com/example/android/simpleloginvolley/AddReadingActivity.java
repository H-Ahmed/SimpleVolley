package com.example.android.simpleloginvolley;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddReadingActivity extends AppCompatActivity {

    private EditText mMeterIdEditView, mValueEditView;
    private Button mSubmitButton;
    private String serverUrl = "http://dev.alsokary.com:8005/api/glocodata";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);

        builder = new AlertDialog.Builder(AddReadingActivity.this);
        mMeterIdEditView = (EditText) findViewById(R.id.add_meter_id);
        mValueEditView = (EditText) findViewById(R.id.add_value);
        mSubmitButton = (Button) findViewById(R.id.add_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String meterId = mMeterIdEditView.getText().toString();
                final String value = mValueEditView.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setTitle("Server Response");
                                builder.setMessage("Response: " + response);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mMeterIdEditView.setText("");
                                        mValueEditView.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Error....", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("meter_id", meterId);
                        params.put("value", value);
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).addToRequestQeue(stringRequest);
            }
        });
    }
}
