package com.poteka.sisbenapp_dev;

import org.ksoap2.serialization.PropertyInfo;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.AsyncResponse;
import com.potekadesignio.sisbenapp_dev.HomeActivity;
import com.potekadesignio.sisbenapp_dev.LoginActivity;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordarPasswordActivity extends Activity implements AsyncResponse 
{
	private TextView txtUsuarioID;
	
	private Button btnRecordarPass;
	private ProgressDialog progDailog;
	
	private String resultadoOperacion = "";
	
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private String WSDL_TARGET_NAMESPACE; //("http://tempuri.org/");
	private String SOAP_ADDRESS; //("http://192.168.1.3:3146/wsNeuroSistemasMobile.asmx");
	
	private OfertaAPP_DataManager callWebService;
	public enum Operacion { SELECT, INSERT, UPDATE, DELETE }

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_recordar_password);
		
		InitVariablesUI();
		initVariables();
		initListenersForViews();
	}
	
	public void InitVariablesUI()
	{
		btnRecordarPass = (Button)findViewById(R.id.btnRecordarPass);
		
		txtUsuarioID = (TextView)findViewById(R.id.txtUsuarioID);
	}
	
	private void initVariables() 
	{
		WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
		SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
				
		callWebService = new OfertaAPP_DataManager();
		dmConsumeWebService = new OfertAPP_DataConnectionInterface();
		dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
		dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
	}
	
	private void initListenersForViews() 
	{
		btnRecordarPass.setOnClickListener(new OnClickListener() 
    	{
    	    	public void onClick(View v) 
    	    	{
    	    		if(ValidarInfo())
    	    		{
    	    			progDailog = ProgressDialog.show(RecordarPasswordActivity.this,"OfertAPP", "Buscando cuenta OfertAPP...",true,true);
        	    		
        	    		String strNombreUsuario = txtUsuarioID.getText().toString();
        	    		
        	    		PropertyInfo piParameters[];
    					piParameters = new PropertyInfo[1];
    			        
    					piParameters[0] = new PropertyInfo();
    					piParameters[0].setName("pCorreoElectronico");
    					piParameters[0].setValue(strNombreUsuario);
    					
    					piParameters[0].setType(String.class);
    					
    					dmConsumeWebService.wsDataManager_OperationName("RecordarContraseñaUsuario");
    					
    					try 
    					{	
    						new WSDataBaseTask().execute(Operacion.INSERT, piParameters, "RecordarContraseñaUsuario");
    					} 
    					catch (Exception ex) 
    					{
    						Toast.makeText(RecordarPasswordActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
    					}
    	    		}
    	    	}
    	});
	}
	
	private boolean ValidarInfo() 
	{
		if(!TextUtils.isEmpty(txtUsuarioID.getText()) && android.util.Patterns.EMAIL_ADDRESS.matcher(txtUsuarioID.getText()).matches())
		{
			return true;
		}
		else
		{
			txtUsuarioID.requestFocus();
			Toast.makeText(RecordarPasswordActivity.this, "Ese correo no me suena...", Toast.LENGTH_LONG).show();	
		}
		
		return false;
	}
	
	
	private class WSDataBaseTask extends AsyncTask<Object, Object, Object> 
	{
		@Override
		protected Object doInBackground(Object... arg0) 
		{
			switch(((Operacion)arg0[0]))
	    	{
				case SELECT:
					break;
				case INSERT:
					try 
					{
						return dmConsumeWebService.ExecWSMethod_Insert((PropertyInfo[])arg0[1], (String)arg0[2]);
					} 
					catch (Exception e) 
					{
						resultadoOperacion = e.getMessage();
					}
					return null;
				case UPDATE:
					break;
				case DELETE:
					break;
				default:
					break;
	    	}
			return null;
			
		} 
		@Override
		protected void onPostExecute(Object result) 
		{
			if(!resultadoOperacion.contains(getString(R.string.ERROR_USUARIO_NO_EXISTE)))
			{
				Toast.makeText(RecordarPasswordActivity.this, "¡Excelente! En breve recibirás un correo con la información necesaria para que ingreses a OfertAPP.", Toast.LENGTH_LONG).show();
				GoToLoginView();
			}
			else
			{
				Toast.makeText(RecordarPasswordActivity.this, "¡Caray! No hemos podido encontrar tu cuenta. Verifica tus datos e intenta de nuevo.", Toast.LENGTH_LONG).show();
			}
			
			progDailog.dismiss();
		}
	}
	
	private void GoToLoginView() 
	{
		Intent myIntent = new Intent(RecordarPasswordActivity.this, LoginActivity.class);
		RecordarPasswordActivity.this.finish();
		startActivity(myIntent);
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void processFinish(Object result) 
	{
		
	}
	
}
