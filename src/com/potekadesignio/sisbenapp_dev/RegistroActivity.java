package com.potekadesignio.sisbenapp_dev;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BLL.DepartamentoBO;
import com.potekadesignio.sisbenapp_dev.BLL.MunicipioBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;
import com.potekadesignio.sisbenapp_dev.utils.GCM_Helper;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroActivity extends Activity implements AsyncResponse
{

	private GCM_Helper gmcHelper; 
	
	private DepartamentoBO dpDepto[];
	private UsuarioBO usUsuario;
	private Spinner ddlDepartamento, ddlMunicipio;
	
	private MunicipioBO mnMuni[];
	private Button btnRegistrar;
	
	private EditText txtNombres;
	private EditText txtApellidos;
	private EditText txtCorreo;
	private EditText txtPassword;
	
	private ProgressDialog progDailog;
	private String resultadoOperacion = "";
		
	private String WSDL_TARGET_NAMESPACE; //("http://tempuri.org/");
	private String SOAP_ADDRESS; //("http://192.168.1.3:3146/wsNeuroSistemasMobile.asmx");
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private OfertaAPP_DataManager callWebService;
	public enum Operacion { SELECT, INSERT, UPDATE, DELETE }
	public enum MetodoDDL { DEPARTAMENTO, MUNICIPIO, CREAR_USUARIO }
	
	// 1 - Android
	// 2 - iOS
	// 3 - Website
	// 4 - Windows Phone
	
	
	public enum InstanciaID { ANDROID }
	
	private MetodoDDL TipoOperacion;
	public String regid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_registro);
		
		initVariables();
		initLayout();
		CargarDatosRegion();
		initListenersForViews();
	}
	
	
	@Override
	protected void onResume() 
	{
	    super.onResume();
	    gmcHelper = new GCM_Helper(getApplicationContext(), RegistroActivity.this);
	    gmcHelper.checkPlayServices();
	}
	
	private void initLayout() 
	{
		ddlDepartamento = (Spinner) findViewById(R.id.ddlDepartamento);
		ddlMunicipio = (Spinner) findViewById(R.id.ddlMunicipio);
		
		btnRegistrar = (Button) findViewById(R.id.btnRegistro);
		
		txtNombres = (EditText)findViewById(R.id.txtNombres);
		txtApellidos = (EditText)findViewById(R.id.txtApellidos);
		txtCorreo = (EditText)findViewById(R.id.txtEmail);
		txtPassword = (EditText)findViewById(R.id.txtPassword);
		
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando datos regionales...",true,true);
	}

	private void initVariables() 
	{
		WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
		SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
				
		callWebService = new OfertaAPP_DataManager();
		dmConsumeWebService = new OfertAPP_DataConnectionInterface();
		dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
		dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
		
		gmcHelper = new GCM_Helper(getApplicationContext(), RegistroActivity.this);
		gmcHelper.verificarGCM();
		
	}
	
	private void CargarDatosRegion()
	{
		PropertyInfo piParameters[] = null;
		
		dmConsumeWebService.wsDataManager_OperationName("ObtenerDepartamentosXML");
		TipoOperacion = MetodoDDL.DEPARTAMENTO;
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "ObtenerDepartamentosXML");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(RegistroActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}
		
	
	}
	
	private void initListenersForViews() 
	{
		btnRegistrar.setOnClickListener(new OnClickListener() 
    	{
    	    	public void onClick(View v) 
    	    	{
    	    		if(ValidarInfo())
    	    		{
	    	    		progDailog = ProgressDialog.show(RegistroActivity.this,"OfertAPP", "Creando cuenta...",true,true);
	    	    		
	    	    		TipoOperacion = MetodoDDL.CREAR_USUARIO;
	    	    		
	    	    		usUsuario = new UsuarioBO();
	    	    		
	    	    		String selectedDepto = ddlDepartamento.getSelectedItem().toString();
	    	    		String selectedMuni = ddlMunicipio.getSelectedItem().toString();
	    	    		
		    			String codDepto = "";
			    		String codMuni = "";
			    		
			    		for(DepartamentoBO depto: dpDepto)
						{
			    			String nombreDepto = depto.getNombreDepto();
			    			if(nombreDepto == selectedDepto)
			    			{
			    				codDepto = depto.getCodigoDeptoDane();
			    				break;
			    			}
						}
			    		
			    		for(MunicipioBO muni: mnMuni)
						{
			    			String nombreMuni = muni.getNombreMuni();
			    			if(nombreMuni == selectedMuni)
			    			{
			    				codMuni = muni.getCodigoMuniDane();
			    				break;
			    			}
						}
	    	    		
			    		usUsuario.setNombresUsuario(txtNombres.getText().toString());
	    	    		usUsuario.setApellidosUsuario(txtApellidos.getText().toString());
	    	    		usUsuario.setCorreoElectronico(txtCorreo.getText().toString());
	    	    		usUsuario.setPassword(txtPassword.getText().toString()); // Aqui hay que encriptar el password
	    	    		usUsuario.setCodigoDepartamento(codDepto);
	    	    		usUsuario.setCodigoMunicipio(codMuni);
	    	    		usUsuario.setInstanciaId(regid);
	    	    		usUsuario.setTipoInstancia(1);
	    	    		
	    	    		CrearUsuario();
	    	    	}
    	    	}
    	});
		
		ddlDepartamento.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        
		    	String selected = parentView.getItemAtPosition(position).toString();
		    	
		    	if(selected  != "Selecciona...")
		    	{
		    		String codDepto = "";
		    		
		    		for(DepartamentoBO depto: dpDepto)
					{
		    			String nombreDepto = depto.getNombreDepto();
		    			if(nombreDepto == selected)
		    			{
		    				codDepto = depto.getCodigoDeptoDane();
		    				break;
		    			}
					}
		    		
		    		TipoOperacion = MetodoDDL.MUNICIPIO;
		    		
		    		progDailog = ProgressDialog.show(RegistroActivity.this,"OfertAPP", "Cargando data...", true, true);
			    	
			    	PropertyInfo piParameters[];
					piParameters = new PropertyInfo[1];
			        
					piParameters[0] = new PropertyInfo();
					piParameters[0].setName("pCodDepto");
					piParameters[0].setValue(codDepto);
					
					piParameters[0].setType(String.class);
			    	
					dmConsumeWebService.wsDataManager_OperationName("ObtenerMunicipiosPorDeptoXML");
					
					try 
					{	
						new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "ObtenerMunicipiosPorDeptoXML");
					} 
					catch (Exception ex) 
					{
						Toast.makeText(RegistroActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
					}
		    	}
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		       
		    }

		});
	}
	
	private boolean ValidarInfo() 
	{
		if(!TextUtils.isEmpty(txtNombres.getText()))
		{
			if(!TextUtils.isEmpty(txtApellidos.getText()))
			{
				if(!TextUtils.isEmpty(txtPassword.getText()))
				{
					if(!TextUtils.isEmpty(txtCorreo.getText()) && android.util.Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText()).matches())
					{
						if (!ddlDepartamento.getSelectedItem().toString().trim().equals("Selecciona...")) 
						{
							if (!ddlMunicipio.getSelectedItem().toString().trim().equals("Selecciona...")) 
							{
								return true;
							}
							else
							{
								ddlMunicipio.requestFocus();
								Toast.makeText(RegistroActivity.this, "Si, también el Municipio... Son las reglas", Toast.LENGTH_LONG).show();
							}
						}
						else
						{
							ddlDepartamento.requestFocus();
							Toast.makeText(RegistroActivity.this, "Caramba! Tienes que seleccionar Departamento", Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						txtCorreo.requestFocus();
						Toast.makeText(RegistroActivity.this, "Ese correo no me suena...", Toast.LENGTH_LONG).show();	
					}
				}
				else
				{
					txtPassword.requestFocus();
					Toast.makeText(RegistroActivity.this, "¿En serio? ¿Sin contraseña?", Toast.LENGTH_LONG).show();	
				}
			}
			else
			{
				txtApellidos.requestFocus();
				Toast.makeText(RegistroActivity.this, "Lo siento, no recuerdo tu apellido.", Toast.LENGTH_LONG).show();	
			}
		}
		else
		{
			txtNombres.requestFocus();
			Toast.makeText(RegistroActivity.this, "Disculpa, ¿cuál es tu nombre?", Toast.LENGTH_LONG).show();	
		}
		
		
		
		return false;
	}
	
	public void CrearUsuario()
	{
		PropertyInfo piParameters[];
		piParameters = new PropertyInfo[8];
        
		piParameters[0] = new PropertyInfo();
		piParameters[0].setName("pNombresUsuario");
		piParameters[0].setValue(usUsuario.getNombresUsuario());
		
		piParameters[0].setType(String.class);
		
		piParameters[1] = new PropertyInfo();
		piParameters[1].setName("pApellidosUsuario");
		piParameters[1].setValue(usUsuario.getApellidosUsuario());
		
		piParameters[1].setType(String.class);
		
		piParameters[2] = new PropertyInfo();
		piParameters[2].setName("pCorreoElectronico");
		piParameters[2].setValue(usUsuario.getCorreoElectronico());
		
		piParameters[2].setType(String.class);
		
		piParameters[3] = new PropertyInfo();
		piParameters[3].setName("pPassword");
		piParameters[3].setValue(usUsuario.getPassword());
		
		piParameters[3].setType(String.class);
		
		piParameters[4] = new PropertyInfo();
		piParameters[4].setName("pCodigoDepto");
		piParameters[4].setValue(usUsuario.getCodigoDepartamento());
		
		piParameters[4].setType(String.class);
		
		piParameters[5] = new PropertyInfo();
		piParameters[5].setName("pCodigoMun");
		piParameters[5].setValue(usUsuario.getCodigoMunicipio());
		
		piParameters[5].setType(String.class);
		
		piParameters[6] = new PropertyInfo();
		piParameters[6].setName("pCodigoInstancia");
		piParameters[6].setValue(usUsuario.getInstanciaId());
		
		piParameters[6].setType(String.class);
		
		piParameters[7] = new PropertyInfo();
		piParameters[7].setName("pTipoInstancia");
		piParameters[7].setValue(usUsuario.getTipoInstancia());
		
		piParameters[7].setType(Integer.class);
		
		dmConsumeWebService.wsDataManager_OperationName("CrearUsuarioXML");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.INSERT, piParameters, "CrearUsuarioXML");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(RegistroActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}
	}
		
	private void GoToHomeView() 
	{
		Intent myIntent = new Intent(RegistroActivity.this, HomeActivity.class);
        startActivity(myIntent);
        RegistroActivity.this.finish();
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
			if(result != null)
			{
				switch(TipoOperacion)
				{
					case DEPARTAMENTO:
				
						dpDepto = callWebService.ObtenerDepartamentos((SoapObject) result);
						
						List<String> listaDeptos = new ArrayList<String>();
						listaDeptos.add("Selecciona...");
						
						for(DepartamentoBO depto: dpDepto)
						{
							listaDeptos.add(depto.getNombreDepto());
						}
						
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistroActivity.this, android.R.layout.simple_spinner_item, listaDeptos);
					    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    ddlDepartamento.setAdapter(adapter);
					    
					    break;
					    
					case MUNICIPIO:
						mnMuni = callWebService.ObtenerMunicipiosDepto((SoapObject) result);
						
						List<String> listaMunis = new ArrayList<String>();
						listaMunis.add("Selecciona...");
						
						for(MunicipioBO muni: mnMuni)
						{
							listaMunis.add(muni.getNombreMuni());
						}
						
						ArrayAdapter<String> adapterMun = new ArrayAdapter<String>(RegistroActivity.this, android.R.layout.simple_spinner_item, listaMunis);
					    adapterMun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					    ddlMunicipio.setAdapter(adapterMun);
					    
						break;
					
					case CREAR_USUARIO:
						
						usUsuario = callWebService.LoginUsuario((SoapObject) result);
						new SaveObject_Helper().GuardarInfoUsuarioBO(usUsuario, getString(R.string.RUTA_NOMBRE_DATOS_USUARIO));
						
						Toast.makeText(RegistroActivity.this, "Tu cuenta ha sido creada. Bienvenido a OfertAPP, " + usUsuario.getNombresUsuario(), Toast.LENGTH_LONG).show();
						GoToHomeView();
					default: 
						break;
				}
			
			}
			else
			{
				if(resultadoOperacion.contains(getString(R.string.ERROR_USUARIO_DUPLICADO)))
				{
					Toast.makeText(RegistroActivity.this, "Oops! Tu cuenta de correo ya ha sido registrada.", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(RegistroActivity.this, "Oops se ha presentado un error; " + resultadoOperacion, Toast.LENGTH_LONG).show();
				}
				
			}
			
			TipoOperacion = null;
			progDailog.dismiss();
			
		}
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
		regid = result.toString();
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.APP_LOCAL_ID), regid);
		editor.commit();
	}
	
}
