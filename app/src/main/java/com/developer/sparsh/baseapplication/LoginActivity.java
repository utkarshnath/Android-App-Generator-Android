package com.developer.sparsh.baseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "D42E2kA11fWW2Nxi98AVKfsqE";
    private static final String TWITTER_SECRET = "C3gjMhktTr85NV8KBRInp99bJRRcxCYu3xu8A2AZbTYtjJrlWz";


    private GoogleApiClient mGoogleApiClient;
    private int Google_SIGN_IN = 1;
    private TextView mStatusTextView;
    private LoginButton fb_loginButton;
    private CallbackManager callbackManager;
    private TwitterLoginButton twitter_loginButton;
    private EditText Username_Edittext;
    private EditText Password_Edittext;
    private Button signInButton;
    private Button signUpButton;
    private SignInButton google_signInButton;
    private RequestQueue requestQueue;
    private final String USER_LOGIN_URL = "http://182.77.125.252/api/v1/login/";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        //Initialize facebook login
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        //Initialize google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        AppEventsLogger.activateApp(this);
        Username_Edittext = (EditText) findViewById(R.id.email);
        Password_Edittext = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        fb_loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        fb_loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        fb_loginButton.registerCallback(callbackManager, mcallback);

        google_signInButton = (SignInButton) findViewById(R.id.google_login_button);
        google_signInButton.setSize(SignInButton.SIZE_WIDE);
        google_signInButton.setOnClickListener(this);
        mStatusTextView = (TextView) findViewById(R.id.text);

        twitter_loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitter_loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_login_button:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, Google_SIGN_IN);
                break;
            case R.id.sign_in_button:
                registerUser();
                break;
            case R.id.sign_up_button:
                //go for sign up
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    //facebook login callbacks
    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Toast.makeText(getApplicationContext(),loginResult.getAccessToken().getApplicationId()+"",Toast.LENGTH_SHORT).show();
            Profile profile = Profile.getCurrentProfile();
            if(profile==null){
                mStatusTextView.setText("null");
            }else {
                mStatusTextView.setText(loginResult.getAccessToken().getApplicationId()+"\n"+ profile.getId());
            }
        }
        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(),"fb login cancel",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(getApplicationContext(),"fb login error",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Google login callbacks
        if (requestCode == Google_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                Toast.makeText(this,"Google login sucessful",Toast.LENGTH_SHORT).show();
                GoogleSignInAccount acct = result.getSignInAccount();
                mStatusTextView.setText(acct.getDisplayName()+"  "+acct.getEmail());

            } else {
                // Signed out, show unauthenticated UI.
                Toast.makeText(this,"Google login failed",Toast.LENGTH_SHORT).show();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

        twitter_loginButton.onActivityResult(requestCode,resultCode,data);
    }



    private void registerUser(){
        final String username = Username_Edittext.getText().toString().trim();
        final String password = Password_Edittext.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_LOGIN_URL,
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
                params.put("key_username",username);
                params.put("key_password",password);
                params.put("type","LOCAL");
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
    private void registerUser(final String token, final String type){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                params.put("tok",token);
                params.put("TYPE",type);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }
}
