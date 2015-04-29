package com.potekadesignio.sisbenapp_dev.utils;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.HomeActivity;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService 
{
	
    String mes;
    private Handler handler;
    
	public static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
	private String TAG = "OfertAPP";
	
	
	
    public GcmMessageHandler() 
    {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() 
    {
        super.onCreate();
        handler = new Handler();
    }
    
    @Override
    protected void onHandleIntent(Intent intent) 
    {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        
        
       mes = extras.getString("alert");
       sendNotification(mes);
       //showToast();
       Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title"));
                
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
            }
         });

    }
    
    private void sendNotification(String msg) 
    {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        		.setSmallIcon(R.drawable.ic_launcher)
        		.setContentTitle("OfertAPP")
        		.setStyle(new NotificationCompat.BigTextStyle()
        		.bigText(msg))
        		.setContentText(msg)
        		.setSound(notification);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        
    }
    
}
