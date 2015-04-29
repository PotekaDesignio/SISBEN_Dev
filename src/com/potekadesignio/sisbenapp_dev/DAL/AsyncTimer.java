package com.potekadesignio.sisbenapp_dev.DAL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncTimer  extends AsyncTask<Void,Integer,Boolean>
{
	 private boolean isRunning;
     private boolean stop;
     private int seconds;
     private Context context;
     private onProgressUpdateListener listener;

     public AsyncTimer(Context c)
     {
         context = c;
     }
     public void setOnprogressUpdateListener(onProgressUpdateListener UpdateListener)
     {
         listener = UpdateListener;
     }

	 @Override
	 protected Boolean doInBackground(Void... arg0) 
	 {
		stop  = false;
        isRunning = true;
        seconds = 0;
        this.publishProgress(seconds);
        while(seconds<10&&stop!=true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
 
                Log.e("AsyncTutorial", e.getMessage());
            }
            seconds++;
            this.publishProgress(seconds);
        }
        if(stop==false)
            return true;
        else
            return false;

	 }
	
	 @Override
     protected void onCancelled() 
	 {
	        stop = true;
	        isRunning = false;
	        Toast.makeText(context, "User stopped thread!", Toast.LENGTH_SHORT).show();
     }
     @Override
     protected void onPostExecute(Boolean result) 
     {
	        isRunning = false;
	        if(result==true)
	            Toast.makeText(context, "Running of 10 second timer complete!", Toast.LENGTH_SHORT).show();
     }
     @Override
     protected void onProgressUpdate(Integer... values) 
     {
    	 listener.progressUpdate(values[0]);
     }
     public boolean getIsRunning()
     {
    	 return isRunning;
     }
     public interface onProgressUpdateListener
     {
    	 public void progressUpdate(int i);
     }
     
     protected void execute()
     {
    	 
     }
}
