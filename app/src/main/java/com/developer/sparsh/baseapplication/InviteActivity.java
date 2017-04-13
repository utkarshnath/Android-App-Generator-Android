package com.developer.sparsh.baseapplication;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.developer.sparsh.baseapplication.Adapters.InviteAdapter;
import com.developer.sparsh.baseapplication.Application.Application;
import com.developer.sparsh.baseapplication.Classes.AdminContact;
import com.developer.sparsh.baseapplication.Interface.OnResponseReceivedListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InviteActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
                                                                 GoogleApiClient.OnConnectionFailedListener,
                                                                 OnResponseReceivedListener, View.OnClickListener {

    private static final int RC_AUTHORIZE_CONTACTS = 100;
    private static final String TAG = "!@#";
    private RecyclerView invite_recyclerview;
    private InviteAdapter adapter = null;
    private ArrayList<AdminContact> list = null;
    private boolean flag = false;
    private String SEND_INVITAION_URL = "http://192.168.43.166:3000/api/v1/invite/";

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String REQUEST_URL = "https://www.google.com/m8/feeds/contacts/default/full?alt=json";


    private Button sendInvitation = null;
    private GoogleApiClient mClient = null;
    private Account authorizedAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        sendInvitation = (Button) findViewById(R.id.send_invitation_button);
        sendInvitation.setEnabled(false);
        invite_recyclerview = (RecyclerView) findViewById(R.id.invite_recycler_view);
        invite_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<AdminContact>();
        adapter = new InviteAdapter(list);
        invite_recyclerview.setAdapter(adapter);
        sendInvitation.setOnClickListener(this);

        // build google api client for authorization
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder().requestEmail()
                .requestScopes(new Scope("https://www.google.com/m8/feeds/")).build();
        mClient = new GoogleApiClient.Builder(this, this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                        .build();
    }

    @Override
    protected void onResume() {
        mClient.connect();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mClient.disconnect();
        super.onPause();
    }

    private void authorizeContactsAccess() {
        if (mClient.isConnected()) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mClient);
            startActivityForResult(signInIntent, RC_AUTHORIZE_CONTACTS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_AUTHORIZE_CONTACTS) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                authorizedAccount = googleSignInAccount.getAccount();
                getContacts();
            }
        }
    }

    private void getContacts() {
        GetContactsTask task = new GetContactsTask(authorizedAccount,this);
        task.execute();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Enable import button
        sendInvitation.setEnabled(true);
        if(flag==false){
            flag = true;
            authorizeContactsAccess();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: Some error occurred");
        Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onResponseReceived(String responseString, JSONObject responseObject, String accessToken) {
        Log.d(TAG, "onResponseReceived: " + responseString);
        try {
            parseJson(responseObject, accessToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void parseJson(JSONObject responseObject , final String accessToken) throws JSONException {
        JSONObject feed = responseObject.getJSONObject("feed");
        int count = feed.getJSONObject("openSearch$totalResults").getInt("$t");
        String ALL_CONTACT_REQUEST_UR = "https://www.google.com/m8/feeds/contacts/default/full?alt=json&max-results="+count;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_CONTACT_REQUEST_UR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject feed = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            feed = jsonObject.getJSONObject("feed");
                            JSONArray contactArray = feed.getJSONArray("entry");
                            for(int i=0;i<contactArray.length();i++) {
                                AdminContact contact = new AdminContact();
                                if(contactArray.getJSONObject(i).getJSONObject("title").getString("$t").equals("")){
                                    continue;
                                }
                                contact.setName(contactArray.getJSONObject(i).getJSONObject("title").getString("$t"));
                                if (contactArray.getJSONObject(i).has("gd$phoneNumber")) {
                                    contact.setNumber(contactArray.getJSONObject(i).getJSONArray("gd$phoneNumber").getJSONObject(0).getString("$t") + "");
                                }
                                if (contactArray.getJSONObject(i).has("gd$email")) {
                                    contact.setEmail(contactArray.getJSONObject(i).getJSONArray("gd$email").getJSONObject(0).getString("address"));
                                }
                                list.add(contact);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                final String authorizationHeader = "Bearer"+ " " + accessToken;
                headers.put("Authorization" , authorizationHeader);
                return headers;
            }
        };
        Application.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        String appId = "588c9af02561712a0c1e93cb";
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                count++;
            }
        }
        Toast.makeText(this, count + "", Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appId",appId);
            JSONArray invitedArray = new JSONArray();
            for(int i=0;i<list.size();i++){
                if(list.get(i).isSelected()){
                    JSONObject invitee = new JSONObject();
                    invitee.put("name",list.get(i).getName());
                    invitee.put("mobile",list.get(i).getNumber());
                    invitee.put("email",list.get(i).getEmail());
                    invitedArray.put(invitee);
                }
            }
            jsonObject.put("invitations",invitedArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendInvitations(jsonObject.toString());
    }

    void sendInvitations(final String invitation){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_INVITAION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,response);
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
                params.put("key_invitations",invitation);

                return params;
            }
        };
        Application.getInstance().getRequestQueue().add(stringRequest);

    }


    public class GetContactsTask extends AsyncTask<Void, Void, String> {
        Account mAccount;
        OnResponseReceivedListener mListener;

        public GetContactsTask(Account mAccount , OnResponseReceivedListener mListener) {
            this.mAccount = mAccount;
            this.mListener = mListener;
        }

        @Override
        protected String doInBackground(Void... params) {
            String accessToken = null;
            try {
                GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(InviteActivity.this,
                        Collections.singleton("https://www.google.com/m8/feeds/"));
                mCredential.setSelectedAccount(mAccount);
                // Retrieve access token
                accessToken = mCredential.getToken();


            } catch (IOException | GoogleAuthException e) {
                e.printStackTrace();
            }
            return accessToken;
        }

        @Override
        protected void onPostExecute(final String accessToken) {
            Log.d(TAG, "onPostExecute: accessToken " + accessToken);
            JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET,
                    REQUEST_URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            mListener.onResponseReceived(response.toString(),response,accessToken);
                            Log.d(TAG,response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        }
                    }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    final String authorizationHeader = "Bearer"+ " " + accessToken;
                    headers.put("Authorization" , authorizationHeader);
                    return headers;
                }
            };
            Application.getInstance().getRequestQueue().add(mRequest);
        }
    }


}


