package com.windmill.atlantisweather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper extends AsyncTask<String, Void, String> {


    // add callback interface
    ICallback callback;

    // setter for callback
    public void setCallback(ICallback callback){
        this.callback = callback;
    }



    @Override
    protected String doInBackground(String... params) {


        StringBuilder out = new StringBuilder();
        try {

            //for Testing: Hardcoded URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=kapfenberg");

            // get the string parameter from execute()
            URL url = new URL(params[0]);

            // create Urlconnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // read inputstrem
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            Log.i("INTERNET", out.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toString(); // return of do in background method is input parameter to the  on-post-execude method
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // do something with results from do-in-background method

        // use a Callback-method to react !!
        callback.handleJSonString(s);

    }
}
