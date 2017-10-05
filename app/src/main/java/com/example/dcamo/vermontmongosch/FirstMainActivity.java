package com.example.dcamo.vermontmongosch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstMainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_NAME = "groupName";
    private static final String MY_SETTINGS = "my_settings";
    SharedPreferences mSettings;


    EditText group;
    Button seacrh;
    String groupName = null;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        seacrh = (Button) findViewById(R.id.button);
        group = (EditText) findViewById(R.id.editText);

        try {
            if(mSettings.contains(APP_PREFERENCES_NAME)) {
                Log.e("zzzzxxxx", mSettings.getString(APP_PREFERENCES_NAME, ""));
                groupName = mSettings.getString(APP_PREFERENCES_NAME, "");
                new ParseTask().execute();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }



    }
    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog pDialog = new ProgressDialog(FirstMainActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                String query = URLEncoder.encode(groupName, "utf-8");

//                URL url = new URL("http://193.151.106.187:3000/api/group/" + query);
                URL url = new URL("http://192.168.0.103:3000/test");
//                URL url = new URL("http://193.151.106.187:3000/api/group/");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();



            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
                Log.d("str", strJson);
            if (strJson.isEmpty()){
                Log.d("ERROR", "STRJSON IS EMPTY");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Lapki \n Try again", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent(FirstMainActivity.this, Main.class);
                intent.putExtra("json_string", strJson);
                startActivity(intent);
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_NAME, groupName);
                editor.apply();

                finish();
            }

        }
    }





    public void onClick(View view) throws IOException {
        groupName = group.getText().toString();
        Log.e("das", groupName);
        new ParseTask().execute();

        Toast toast = Toast.makeText(getApplicationContext(),
                groupName, Toast.LENGTH_SHORT);
        toast.show();
    }
}
