package com.potekadesignio.sisbenapp_dev.utils;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.AsyncResponse;

public class RegisterInBackground extends AsyncTask<Object, Object, Object>
{
	public AsyncResponse delegate = null;
	
	private Context context;
	private GoogleCloudMessaging gcm;
	private String regid;
	private String SENDER_ID; //"741403836434"; // REEMPLAZAR POR LA QUE ESTÁ EN STRINGS
	
        @Override
        protected Object doInBackground(Object... arg0)
        {
        	context = (Context)arg0[0];
        	gcm = (GoogleCloudMessaging)arg0[1];
        	SENDER_ID =  context.getString(R.string.SENDER_ID);   
        	
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);
                return regid;
            } 
            catch (IOException ex) 
            {
                msg = "Error :" + ex.getMessage();
                return msg;
            }
        }
        @Override
		protected void onPostExecute(Object result) 
		{
        	delegate.processFinish(result);
		}
    
}