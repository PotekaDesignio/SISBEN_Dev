package com.potekadesignio.sisbenapp_dev.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.potekadesignio.sisbenapp_dev.AsyncResponse;

import android.os.AsyncTask;
import android.util.Log;

public class HttpAsyncTask extends AsyncTask<String, Void, String> 
{
	public AsyncResponse delegate = null;
    @Override
    protected String doInBackground(String... urls) 
    {

        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) 
    {
    	delegate.processFinish(result);
    }
    
    public static String GET(String url)
    {
        InputStream inputStream = null;
        String result = "";
    
        try 
        {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
    
    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
    
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        
        return result;
 
    }
}