package com.developer.sparsh.baseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_name,editText_password,editText_confirm_password,editText_email,editText_phone_no;
    private Button button_signup;
    private RequestQueue requestQueue;
    private String USER_SIGN_UP_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editText_name = (EditText) findViewById(R.id.name);
        editText_password = (EditText) findViewById(R.id.password);
        editText_confirm_password = (EditText) findViewById(R.id.confirm_password);
        editText_email = (EditText) findViewById(R.id.email);
        editText_phone_no = (EditText) findViewById(R.id.phone_no);
        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    //TO DO
    //MAKE BUTTON CLICKABLE ONLY WHEN ALL THE FEILDS ARE FILLED
    //CAN MAKE PHONE NUMBER OPTIONAL
    
    @Override
    public void onClick(View v) {
        final String name = editText_name.getText().toString().trim();
        final String password = editText_password.getText().toString().trim();
        final String e_mail = editText_email.getText().toString().trim();
        final String phone_no = editText_phone_no.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_SIGN_UP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("!@#",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("key_username",e_mail);
                params.put("key_password",password);
                params.put("key_name",name);
                params.put("key_phone",phone_no);
                params.put("type","LOCAL");
                return params;
            }

        };
        requestQueue.add(stringRequest);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
