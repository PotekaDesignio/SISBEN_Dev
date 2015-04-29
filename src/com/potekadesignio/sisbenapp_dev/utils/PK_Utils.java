package com.potekadesignio.sisbenapp_dev.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class PK_Utils {
	
	
	public void Helvetica_Bold(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueBold.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Helvetica_Regular(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeue.ttf"); 
		pControl.setTypeface(type);
	}

	public void Helvetica_Light(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueLight.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Helvetica_Italic(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueItalic.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Lucida_Regular(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/LucidaSansRegular.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Lucida_Italic(AssetManager pAssets, TextView pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/LucidaSansItalic.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Helvetica_Bold(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueBold.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Helvetica_Regular(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeue.ttf"); 
		pControl.setTypeface(type);
	}

	public void Helvetica_Light(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueLight.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Helvetica_Italic(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/HelveticaNeueItalic.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Lucida_Regular(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/LucidaSansRegular.ttf"); 
		pControl.setTypeface(type);
	}
	
	public void Lucida_Italic(AssetManager pAssets, Button pControl)
	{
		Typeface type = Typeface.createFromAsset(pAssets,"fonts/LucidaSansItalic.ttf"); 
		pControl.setTypeface(type);
	}
	
	public static boolean isConnected(Context context, Activity origenSolicitud)
	{
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(origenSolicitud.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if (networkInfo != null && networkInfo.isConnected()) 
            return true;
        else
            return false;   
    }
	
	public OfertaSimpleBO[] LeerOfertasMOR(String pStrOfertasMOR) throws JSONException
	{
		JSONObject jsonObjMOR = new JSONObject(pStrOfertasMOR);
		JSONArray jsonArrayMOR = jsonObjMOR.getJSONArray("d");
		
		OfertaSimpleBO osOfertasMOR[] = new OfertaSimpleBO[jsonArrayMOR.length()];
				
	    for (int i = 0; i < jsonArrayMOR.length(); i++) 
	    {
	        JSONObject explrObject = jsonArrayMOR.getJSONObject(i);
	        osOfertasMOR[i] = new OfertaSimpleBO();
	        
	        osOfertasMOR[i].setDimension(explrObject.getString("dimension").equals("") ? "N/A" :  explrObject.getString("dimension"));
	        osOfertasMOR[i].setNumero(explrObject.getString("numero").equals("") ? "N/A" :  explrObject.getString("numero"));
	        osOfertasMOR[i].setEntidad(explrObject.getString("entidad").equals("") ? "N/A" :  explrObject.getString("entidad"));
	        osOfertasMOR[i].setOferta(explrObject.getString("oferta").equals("") ? "N/A" :  explrObject.getString("oferta"));
	        osOfertasMOR[i].setLogro(explrObject.getString("logro").equals("") ? "N/A" :  explrObject.getString("logro"));
	        osOfertasMOR[i].setQueEs(explrObject.getString("quees").equals("") ? "N/A" :  explrObject.getString("quees"));
	        osOfertasMOR[i].setParaQuien(explrObject.getString("paraquien").equals("") ? "N/A" :  explrObject.getString("paraquien"));
	        osOfertasMOR[i].setDonde(explrObject.getString("donde").equals("") ? "N/A" :  explrObject.getString("donde"));
	        osOfertasMOR[i].setRequisitos(explrObject.getString("requisitos").equals("") ? "No" :  explrObject.getString("requisitos"));
	        osOfertasMOR[i].setTip(explrObject.getString("tip").equals("") ? "Ninguno" :  explrObject.getString("tip"));
	        
	    }
	    
	    return osOfertasMOR;
	}
	
	public static void slide_up(Context ctx, View v)
	{
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
		if(a != null)
		{
			a.reset();
			
			if(v != null)
			{
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
	
	public static void slide_down(Context ctx, View v)
	{
		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
		if(a != null)
		{
			a.reset();
			
			if(v != null)
			{
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
	
	public void SincronizarCalendario(Context contexto)
	{
		AccountManager am = AccountManager.get(contexto);
	    Account[] acc = am.getAccountsByType("com.google");
	    Account account = null;
		    
	    if (acc.length > 0) 
	    {
	        account = acc[0];

	        Bundle extras = new Bundle();
	        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
	        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

	        ContentResolver.requestSync(account, "com.android.calendar", extras);
	    }
	}
	
	public int ListSelectedCalendars(Context context, String eventtitle) 
	{
	    Uri eventUri;
	    if (android.os.Build.VERSION.SDK_INT <= 7) {
	        // the old way

	        eventUri = Uri.parse("content://calendar/events");
	    } else {
	        // the new way

	        eventUri = Uri.parse("content://com.android.calendar/events");
	    }

	    int result = 0;
	    String projection[] = { "_id", "title", "deleted" };
	    Cursor cursor = context.getContentResolver().query(eventUri, null, null, null, null);

	    if (cursor.moveToFirst()) 
	    {
	    	String calID;
	        String calName;
	        String calDelete;

	        int idCol = cursor.getColumnIndex(projection[0]);
	        int nameCol = cursor.getColumnIndex(projection[1]);
	        int deleteCol = cursor.getColumnIndex(projection[2]);
	        
	        do 
	        {
	        	calID = cursor.getString(idCol);
	        	calName = cursor.getString(nameCol);
	            calDelete = cursor.getString(deleteCol);
	            
	            if (calName != null && calName.contains(eventtitle) && !calDelete.contains("1"))
	            {
	            	 result = Integer.parseInt(calID);
	            }

	        } 
	        while (cursor.moveToNext());
	        cursor.close();
	    }

	    return result;

	}
	
	public int DeleteCalendarEntry(Context context, int entryID) 
	{
		  int iNumRowsDeleted = 0;

		  Uri eventsUri = Uri.parse(getCalendarUriBase().toString());
		  Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);
		  iNumRowsDeleted = context.getContentResolver().delete(eventUri, null, null);

		  //Log.i("OfertAPP - Deleted " + iNumRowsDeleted + " calendar entry.");

		  return iNumRowsDeleted;
	}

	public void EliminarOfertasAgendadas(Context contexto,  ArrayList<OfertaCompletaBO> osOfertasAgendadas)
	{
		
		Uri eventUri;
	    if (android.os.Build.VERSION.SDK_INT <= 7) {
	        // the old way

	        eventUri = Uri.parse("content://calendar/events");
	    } else {
	        // the new way

	        eventUri = Uri.parse("content://com.android.calendar/events");
	    }

	    int result = 0;
	    String projection[] = { "_id", "title", "deleted" };
	    
	    for(OfertaCompletaBO oferta : osOfertasAgendadas)
		{
	    	Cursor cursor = contexto.getContentResolver().query(eventUri, null, null, null, null);

		    if (cursor.moveToFirst()) {

		    	String calID;
		    	String calName;
		        String calDelete;

		        int idCol = cursor.getColumnIndex(projection[0]);
		        int nameCol = cursor.getColumnIndex(projection[1]);
		        int deleteCol = cursor.getColumnIndex(projection[2]);
		        
		        do 
		        {
		        	calID = cursor.getString(idCol);
		        	calName = cursor.getString(nameCol);
		            calDelete = cursor.getString(deleteCol);
		            
		            if (calName != null && calName.contains(oferta.getOfe_NombreOferta()) && !calDelete.contains("1"))
		            {
		            	result = Integer.parseInt(calID);
		            	DeleteCalendarEntry(contexto, result);
		            }
		        } 
		        while (cursor.moveToNext());
		        cursor.close();
		    }
		}
	    
	}
	
	private Uri getCalendarUriBase() 
	{
	    Uri eventUri;
	    if (android.os.Build.VERSION.SDK_INT <= 7) {
	        // the old way

	        eventUri = Uri.parse("content://calendar/events");
	    } else {
	        // the new way

	        eventUri = Uri.parse("content://com.android.calendar/events");
	    }

	    return eventUri;
	}
	
}
