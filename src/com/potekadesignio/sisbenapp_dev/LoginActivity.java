package com.potekadesignio.sisbenapp_dev;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

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
import android.widget.Toast;
import android.widget.TextView;

import com.poteka.ofertapp_dev.R;
import com.poteka.sisbenapp_dev.RecordarPasswordActivity;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;
import com.potekadesignio.sisbenapp_dev.utils.GCM_Helper;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

public class LoginActivity extends Activity implements AsyncResponse
{

	private GCM_Helper gmcHelper; 
	private UsuarioBO usUsuario;
	
	private TextView txtUsuarioID;
	private TextView txtContraseña;
	
	private Button btnIniciarSesion;
	private ProgressDialog progDailog;
	
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private String WSDL_TARGET_NAMESPACE; //("http://tempuri.org/");
	private String SOAP_ADDRESS; //("http://192.168.1.3:3146/wsNeuroSistemasMobile.asmx");
	
	private OfertaAPP_DataManager callWebService;
	public enum Operacion { SELECT, INSERT, UPDATE, DELETE }
	public enum MetodoDDL { DEPARTAMENTO, MUNICIPIO, CREAR_USUARIO }
	public String regid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_login);
		
		InitVariablesUI();
		initVariables();
		initListenersForViews();
				
	}
	
	public void InitVariablesUI()
	{
		btnIniciarSesion = (Button)findViewById(R.id.btnIniciarSesion);
		
		txtUsuarioID = (TextView)findViewById(R.id.txtUsuarioID);
		txtContraseña = (TextView)findViewById(R.id.txtPassword);
	}
	
	private void initVariables() 
	{
		WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
		SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
				
		callWebService = new OfertaAPP_DataManager();
		dmConsumeWebService = new OfertAPP_DataConnectionInterface();
		dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
		dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
		
		gmcHelper = new GCM_Helper(getApplicationContext(), LoginActivity.this);
		gmcHelper.verificarGCM();
	}
	
	
	private void initListenersForViews() 
	{
		btnIniciarSesion.setOnClickListener(new OnClickListener() 
    	{
    	    	public void onClick(View v) 
    	    	{
    	    		if(ValidarInfo())
    	    		{
    	    			progDailog = ProgressDialog.show(LoginActivity.this,"OfertAPP", "Iniciando sesión...",true,true);
        	    		
        	    		String strNombreUsuario = txtUsuarioID.getText().toString();
        	    		String strPassword = txtContraseña.getText().toString();
        	    		
        	    		PropertyInfo piParameters[];
    					piParameters = new PropertyInfo[4];
    			        
    					piParameters[0] = new PropertyInfo();
    					piParameters[0].setName("pCorreoUsuario");
    					piParameters[0].setValue(strNombreUsuario);
    					
    					piParameters[0].setType(String.class);
    					
    					piParameters[1] = new PropertyInfo();
    					piParameters[1].setName("pPassword");
    					piParameters[1].setValue(strPassword);
    					
    					piParameters[1].setType(String.class);
    					
    					piParameters[2] = new PropertyInfo();
    					piParameters[2].setName("pInstanciaID");
    					piParameters[2].setValue(regid);
    					
    					piParameters[2].setType(String.class);
    					
    					piParameters[3] = new PropertyInfo();
    					piParameters[3].setName("pTipoInstancia");
    					piParameters[3].setValue(1);
    					
    					piParameters[3].setType(Integer.class);
    					
    			    	
    					dmConsumeWebService.wsDataManager_OperationName("LoginUsuarioXML");
    					
    					try 
    					{	
    						new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "LoginUsuarioXML");
    					} 
    					catch (Exception ex) 
    					{
    						Toast.makeText(LoginActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
    					}
    	    		}
    	    	}
    	});
	}
	
	public void txtOlvidarPass_click(View v)
	{
		GoToRecordarPasswordActivity();
	}
	
	private void GoToRecordarPasswordActivity() 
	{
		Intent myIntent = new Intent(LoginActivity.this, RecordarPasswordActivity.class);
		LoginActivity.this.finish();
		startActivity(myIntent);
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
	}

	private void GoToHomeView() 
	{
		Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
		LoginActivity.this.finish();
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
	protected void onResume() 
	{
	    super.onResume();
	    gmcHelper = new GCM_Helper(getApplicationContext(), LoginActivity.this);
	    gmcHelper.checkPlayServices();
	}
	
	private class WSDataBaseTask extends AsyncTask<Object, Object, Object> 
	{
		@Override
		protected Object doInBackground(Object... arg0) 
		{
			switch(((Operacion)arg0[0]))
	    	{
				case SELECT:
					return dmConsumeWebService.ExecWSMethod_Select((PropertyInfo[])arg0[1], (String)arg0[2]);	
				case INSERT:
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
			if(result != null)
			{
				usUsuario = callWebService.LoginUsuario((SoapObject) result);
				new SaveObject_Helper().GuardarInfoUsuarioBO(usUsuario, getString(R.string.RUTA_NOMBRE_DATOS_USUARIO));
				Toast.makeText(LoginActivity.this, "Bienvenido de nuevo a OfertAPP, " + usUsuario.getNombresUsuario(), Toast.LENGTH_LONG).show();

				GoToHomeView();
				
			}
			else
			{
				Toast.makeText(LoginActivity.this, "¡Caray! No hemos podido encontrar tu cuenta. Verifica tus datos e intenta de nuevo.", Toast.LENGTH_LONG).show();
			}
			
			progDailog.dismiss();
			
		}
	}
	
	private boolean ValidarInfo() 
	{
		if(!TextUtils.isEmpty(txtUsuarioID.getText()))
		{
			if(!TextUtils.isEmpty(txtContraseña.getText()))
			{
				return true;
			}
			else
			{
				txtContraseña.requestFocus();
				Toast.makeText(LoginActivity.this, "¿No se te olvida algo?", Toast.LENGTH_LONG).show();	
			}
		}
		else
		{
			txtUsuarioID.requestFocus();
			Toast.makeText(LoginActivity.this, "Ese correo no me suena...", Toast.LENGTH_LONG).show();	
		}
		
		return false;
	}

	@Override
	public void processFinish(Object result) 
	{
		regid = result.toString();
		
		SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.APP_LOCAL_ID), regid);
		editor.commit();
		
		
		
	}
}
