package com.example.android.simpleloginvolley;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button reg_bn;
    private EditText mName, mEmail, mUserName, mPassword, mConPassword;
    String sName, sEmail, sUserName, sPassword, sConPassword;
    AlertDialog.Builder builder;
    String reg_url = "http://192.168.1.5:8888/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_bn = (Button) findViewById(R.id.bn_reg);
        mName = (EditText)findViewById(R.id.reg_name);
        mEmail = (EditText)findViewById(R.id.reg_email);
        mUserName = (EditText)findViewById(R.id.reg_user_name);
        mPassword = (EditText)findViewById(R.id.reg_password);
        mConPassword = (EditText)findViewById(R.id.reg_con_password);
        builder = new AlertDialog.Builder(RegisterActivity.this);
        reg_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = mName.getText().toString();
                sEmail = mEmail.getText().toString();
                sUserName = mUserName.getText().toString();
                sPassword = mPassword.getText().toString();
                sConPassword = mConPassword.getText().toString();
                if(sName.equals("") || sEmail.equals("") || sUserName.equals("") || sPassword.equals("") || sConPassword.equals("")){
                    builder.setMessage("Please fill all the fields...");
                    displayAlert("input_error");

                }else {
                    if(!sPassword.equals(sConPassword)){
                        builder.setTitle("Something went wrong...");
                        builder.setMessage("Your passwords are not matching....");
                        displayAlert("input_error");
                    }
                    else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response...");
                                            builder.setMessage(message);
                                            displayAlert(code);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("name", sName);
                                params.put("email", sEmail);
                                params.put("user_name", sUserName);
                                params.put("password", sPassword);

                                return params;
                            }
                        };
                        MySingleton.getInstance(getApplicationContext()).addToRequestQeue(stringRequest);
                    }
                }

            }
        });

    }

    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(code.equals("input_error")){
                    mPassword.setText("");
                    mConPassword.setText("");
                } else if(code.equals("reg_success")){
                    finish();
                } else if(code.equals("reg_failed")){
                    mName.setText("");
                    mEmail.setText("");
                    mUserName.setText("");
                    mPassword.setText("");
                    mConPassword.setText("");

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
