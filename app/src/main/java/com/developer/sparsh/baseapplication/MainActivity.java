package com.developer.sparsh.baseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv_hello_view);

        // Read the JSON file and update the text view
        String json = readJsonFile("data.json");
        try {

            Log.d(TAG, "onCreate: " + json);
            JSONObject baseObject = new JSONObject(json);
            String textToSet = baseObject.getString("text");
            textView.setText(textToSet);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String readJsonFile(String filename){
        try{
            InputStream inputStream = this.getAssets().open(filename);
            int size = inputStream.available();

            byte buffer[] = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer,"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
