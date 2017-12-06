package com.example.android.simpleloginvolley;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    private TextView registerTextView;
    private Button mLoginButton;
    private EditText mUserName, mPassword;
    private String sUserName, sPassword;
    private String loginUrl = "http://192.168.1.5:8888/login.php";
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerTextView = (TextView) findViewById(R.id.reg_txt);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        builder = new AlertDialog.Builder(LoginActivity.this);
        mLoginButton = (Button) findViewById(R.id.bn_login);
        mUserName = (EditText)findViewById(R.id.login_name);
        mPassword = (EditText)findViewById(R.id.login_password);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUserName = mUserName.getText().toString();
                sPassword = mPassword.getText().toString();
                if(sUserName.equals("") || sPassword.equals("")){
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid usernaem and password...");
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if(code.equals("login_failed")){
                                            builder.setTitle("Login Error...");
                                            displayAlert(jsonObject.getString("message"));
                                        }else {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("name", jsonObject.getString("name"));
                                            bundle.putString("email", jsonObject.getString("email"));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_name", sUserName);
                            params.put("password", sPassword);
                            return params;
                        }
                    };
                    MySingleton.getInstance(getApplicationContext()).addToRequestQeue(stringRequest);
                }
            }
        });

    }
    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserName.setText("");
                mPassword.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
