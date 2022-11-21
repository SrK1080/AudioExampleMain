package com.example.audioexamplemain;

import android.os.AsyncTask;
import android.app.AlertDialog;
import android.content.Context;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    AlertDialog dialog;
    Context context;
    public BackgroundWorker(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        dialog=new AlertDialog.Builder(context).create();
        dialog.setTitle("status");
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
    }



    @Override
    protected String doInBackground(String... params) {
        String date = params[0];
        String time = params[1];
        String deviceId = params[2];
        String lat = params[3];
        String longi = params[4];
        String sample= params[5];
        //String sample=params[4];
        String login="http://10.241.64.219/insertdata.php";
        try{
        URL url=new URL(login);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

        String post_data = "";

            post_data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                    + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")+ "&"
                    + URLEncoder.encode("deviceId", "UTF-8") + "=" + URLEncoder.encode(deviceId, "UTF-8")+ "&"
                    + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8")+"&"+
                        URLEncoder.encode("longi", "UTF-8") + "=" + URLEncoder.encode(longi, "UTF-8")+"&"+
            URLEncoder.encode("sample", "UTF-8") + "=" + URLEncoder.encode(sample, "UTF-8");
                    //+ URLEncoder.encode("sample", "UTF-8") + "=" + URLEncoder.encode(sample, "UTF-8");



        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
        return null;
    }
}
