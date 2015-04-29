package com.potekadesignio.sisbenapp_dev.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.AsyncResponse;

public class GCM_Helper
{
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "1";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
    public String regid;
	
    private Context context;
    private Activity origenActivity;
    
	
	private GoogleCloudMessaging gcm;
	private String TAG = "OfertAPP";
	
	RegisterInBackground ribGCMID = new RegisterInBackground();
	
	public String getRegistroGCMID()
	{
		return regid;
	}
	
	public GCM_Helper(Context pContext, Activity pActivity)
	{
		context = pContext;
		origenActivity = pActivity;
	}
	
	public void verificarGCM() 
	{
		// Check device for Play Services APK.
	    if (checkPlayServices()) 
	    {
	    	gcm = GoogleCloudMessaging.getInstance(origenActivity);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) 
            {
            	ribGCMID = new RegisterInBackground();
        		ribGCMID.delegate = (AsyncResponse) origenActivity;
            	ribGCMID.execute(context, gcm, null);
            }
	    }
	    else 
	    {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}

	public boolean checkPlayServices() 
	{
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(origenActivity);
	    
	    if (resultCode != ConnectionResult.SUCCESS) 
	    {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) 
	        {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, origenActivity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } 
	        else 
	        {
	            
	        	Toast.makeText(origenActivity, "Éste teléfono no soporta notificaciones PUSH.", Toast.LENGTH_LONG).show();
	            return false;
	        }
	        return false;
	    }
	    return true;
	}

	private String getRegistrationId(Context context) 
	{
	    final SharedPreferences prefs = getGCMPreferences(context);
	    
	    String registrationId = prefs.getString(context.getString(R.string.APP_LOCAL_ID), "");
	    
	    if (registrationId.isEmpty()) 
	    {
	    	Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    
	    int registeredVersion = prefs.getInt(context.getString(R.string.PROPERTY_APP_VERSION), Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    
	    if (registeredVersion != currentVersion) 
	    {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    
	    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context) 
	{
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return origenActivity.getSharedPreferences(origenActivity.getClass().getSimpleName(), Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) 
	{
	    try 
	    {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        
	        return packageInfo.versionCode;
	    } 
	    catch (NameNotFoundException e) {
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
}
