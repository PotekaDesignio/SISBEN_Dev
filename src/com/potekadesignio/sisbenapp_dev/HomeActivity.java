package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.poteka.ofertapp_dev.R;
import com.poteka.sisbenapp_dev.HojaOfertaActivity;

import com.potekadesignio.sisbenapp_dev.BLL.BitacoraBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity 
{

	private OfertaCompletaBO ocOfertasWS[];
	private UsuarioBO usAnteriorUsuario;
	
	private ListView listView;
	private ProgressDialog progDailog;
	
	private TextView txtIntroMensaje;
	
	private String WSDL_TARGET_NAMESPACE; 
	private String SOAP_ADDRESS; 
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private OfertaAPP_DataManager callWebService;
	private enum Operacion { SELECT, INSERT, UPDATE, DELETE }
	
	private SharedPreferences sharedPref;
	
	private Boolean bolCerrarSesion = false;
	private ArrayList<OfertaCompletaBO> osOfertasAgendadas;
	private OfertaSimpleBO osOfertasMOR[];
	private ArrayList<BitacoraBO> boBitacorasUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_home);
		
		InitLayout();
		InitVariables();
	}
	
	@SuppressWarnings("unchecked")
	public void InitVariables()
	{
		WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
		SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
		
		usAnteriorUsuario = (UsuarioBO)new SaveObject_Helper().CargarInfoSerializadaUsuarioBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		
		if(usAnteriorUsuario == null)
		{
			progDailog.dismiss();
			Toast.makeText(HomeActivity.this, "¡Caramba! Al parecer tu sesión ha sido cerrada. Pero no te preocupes, aquí podrás volver a iniciarla...", Toast.LENGTH_LONG).show();
			GoToLoginView();
		}
		else
		{
			ocOfertasWS = (OfertaCompletaBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS)));
			osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
			boBitacorasUsuario = (ArrayList<BitacoraBO>)new SaveObject_Helper().CargarInfoSerializadaBitacorasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
			
			txtIntroMensaje.setText("¡" + usAnteriorUsuario.getNombresUsuario() +   ", Bienvenido a OfertAPP!\n" +
									"Aquí encontrarás todas las herramientas que te brinda la aplicación.");
					
			if(ocOfertasWS == null)
			{
				callWebService = new OfertaAPP_DataManager();
				dmConsumeWebService = new OfertAPP_DataConnectionInterface();
				dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
				dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
				
				if(PK_Utils.isConnected(getApplicationContext(), HomeActivity.this))
				{
					CargarDatosOfertasWS();
				}
				else
				{
					progDailog.dismiss();
					Toast.makeText(HomeActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				initMenuItems();
			}
		}
		
	}
	
	private void InitLayout() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando menú de Usuario...", true, true);
		listView = (ListView) findViewById(R.id.list);
		txtIntroMensaje = (TextView) findViewById(R.id.txtIntroMensaje);
		listView = (ListView)findViewById(R.id.list);
	}
	
	private void CargarDatosOfertasWS()
	{
		PropertyInfo piParameters[];
		piParameters = new PropertyInfo[1];
		
		piParameters[0] = new PropertyInfo();
		piParameters[0].setName("pUsuarioID");
		piParameters[0].setValue(usAnteriorUsuario.getUsuarioId());
		
		piParameters[0].setType(Integer.class);
		
		dmConsumeWebService.wsDataManager_OperationName("DescargarInfoOfertaXML");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "DescargarInfoOfertaXML");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(HomeActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void initMenuItems() 
	{
        
		final ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
		
		
        strTitulo.add("MIS OFERTAS DISPONIBLES"); 
        strTitulo.add("HOJA DE OFERTA");
        //strTitulo.add("PRIORIZACIÓN DE OFERTA");
        strTitulo.add("ENCUESTA DE OFERTA");
        strTitulo.add("MIS HISTORIAS");
        strTitulo.add("MANUAL DE OFERTAS Y RUTAS");
        strTitulo.add("CERRAR SESIÓN");
        
        /*
        if(ocOfertasWS == null)
        {
        	strSubTitulo.add("No tienes Ofertas.... por ahora!");	
        }
        else
        {
        	strSubTitulo.add("Tienes " + ocOfertasWS.length + " Ofertas Disponibles");	
        }
        */
        
        strSubTitulo.add("Ofertas para tu Municipio");
        
        /*
        if(osOfertasAgendadas == null || osOfertasAgendadas.size() == 0)
        {
        	strSubTitulo.add("¡No tienes Ofertas por calificar!");	
        }
        else
        {
        	strSubTitulo.add("Tienes " + osOfertasAgendadas.size() + " Ofertas por Calificar");	
        }
        */
        
        strSubTitulo.add("Ofertas pendientes por Calificar");	
        
        //strSubTitulo.add("Solicita Ofertas Prioritarias");
        strSubTitulo.add("Participa en la Encuesta");
        strSubTitulo.add("Tus apuntes de Cogestor");
        strSubTitulo.add("Actualizado 26 de junio de 2014");
        strSubTitulo.add("Finalizas tu sesión en este dispositivo");
        
        intImageId.add(R.drawable.boton_ofertadisp);
        intImageId.add(R.drawable.boton_hevento);
        intImageId.add(R.drawable.boton_peticion);
        intImageId.add(R.drawable.boton_bitacora);
        intImageId.add(R.drawable.boton_mor);
        intImageId.add(R.drawable.boton_mor);
        
        intBotonFondoId.add(R.drawable.fondo_home);
        intBotonFondoId.add(R.drawable.fondo_home);
        intBotonFondoId.add(R.drawable.fondo_home);
		intBotonFondoId.add(R.drawable.fondo_home);
		intBotonFondoId.add(R.drawable.fondo_mor);
		intBotonFondoId.add(R.drawable.fondo_familia);
        
		/*
		if(ocOfertasWS == null)
        {
			intNumOfertas.add(0);	
        }
        else
        {
        	intNumOfertas.add(ocOfertasWS.length);	
        }
        */
		
		intNumOfertas.add(0);
        
		/*
		if(osOfertasAgendadas == null)
        {
			intNumOfertas.add(0);	
        }
        else
        {
        	intNumOfertas.add(osOfertasAgendadas.size());	
        }
        */
		intNumOfertas.add(0);
		
		intNumOfertas.add(0);
		
        /*
		if(boBitacorasUsuario != null)
        {
        	intNumOfertas.add(boBitacorasUsuario.size());
        }
        else
        {
        	intNumOfertas.add(0);
        }
        */
		
		intNumOfertas.add(0);
        
		intNumOfertas.add(0);
        intNumOfertas.add(0);
        		
        CustomList adapter = new CustomList(HomeActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {
	        	switch(position)
	        	{
	        		case 0:
	        			if(ocOfertasWS != null)
	        			{
	        				GoToOfertasView();	
	        			}
	        			else
	        			{
	        				//Toast.makeText(HomeActivity.this, "Paciencia... Pronto serás notificado de nuevas Ofertas para ti.", Toast.LENGTH_LONG).show();
	        				Toast.makeText(HomeActivity.this, "Paciencia... Pronto se publicarán Nuevas Ofertas.", Toast.LENGTH_LONG).show();
	        			}
	        			
	        			break;
	        		case 1:
	        			if(osOfertasAgendadas != null && osOfertasAgendadas.size() != 0)
	        			{
	        				GoToHojaOfertaView();	
	        			}
	        			else
	        			{
	        				Toast.makeText(HomeActivity.this, "¡Oh-Oh! No tienes ninguna Oferta para Calificar. Agéndate a unas cuantas y aqui te esperamos!", Toast.LENGTH_LONG).show();
	        			}
	        			
	        			break;
	        		case 2:
	        			GoToPriorizacionView();
	        			break;
	        		case 3:
	        			GoToBitacorasView();
	        			break;
	        		case 4:
	        			GoToMORActivity();
	        			break;
	        		case 5:
	        			if(PK_Utils.isConnected(getApplicationContext(), HomeActivity.this))
    					{
	        				progDailog = ProgressDialog.show(HomeActivity.this,"OfertAPP", "Cerrando sesión...", true, true);
	        				CerrarSesionInstancia();	
    					}
	        			else
	        			{
	        				Toast.makeText(HomeActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
	        			}
		        		break;
	        		default:
	        			Toast.makeText(HomeActivity.this, "En desarrollo: " + strTitulo.get(+ position), Toast.LENGTH_SHORT).show();	
		        			
	        			
	        	}
	        	
			}
        });
        progDailog.dismiss();
	}
	
	@Override
	public void onResume()
    {  
		super.onResume();
		osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
		boBitacorasUsuario = (ArrayList<BitacoraBO>)new SaveObject_Helper().CargarInfoSerializadaBitacorasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
	    listView.setAdapter(null);
	    InitVariables();
     }
	
	protected void CerrarSesionInstancia() 
	{
		if(callWebService == null)
		{
			callWebService = new OfertaAPP_DataManager();
			dmConsumeWebService = new OfertAPP_DataConnectionInterface();
			dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
			dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
		}
		
		sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
		String strAppID = sharedPref.getString(getString(R.string.APP_LOCAL_ID), "N/A");
		
		PropertyInfo piParameters[];
		piParameters = new PropertyInfo[2];
		
		piParameters[0] = new PropertyInfo();
		piParameters[0].setName("pCodigoInstancia");
		piParameters[0].setValue(strAppID);
		
		piParameters[0].setType(String.class);
		
		piParameters[1] = new PropertyInfo();
		piParameters[1].setName("pUsuarioId");
		piParameters[1].setValue(usAnteriorUsuario.getUsuarioId());
		
		piParameters[1].setType(Integer.class);
		
		dmConsumeWebService.wsDataManager_OperationName("CerrarSesionInstanciaUsuario");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.UPDATE, piParameters, "CerrarSesionInstanciaUsuario");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(HomeActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}
		
	}

	private void EliminarDatosUsuario() 
	{
		
		new PK_Utils().EliminarOfertasAgendadas(getApplicationContext(), osOfertasAgendadas);
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS)));
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_DIMENSIONES)));
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_MOR)));
		new SaveObject_Helper().EliminarArchivoBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
		
	}

	private void GoToOfertasView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, OfertasActivity.class);
        startActivity(myIntent);
       
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
	}
	
	private void GoToHojaOfertaView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, HojaOfertaActivity.class);
        startActivity(myIntent);
       
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	private void GoToPriorizacionView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, PriorizacionOfertaActivity.class);
        startActivity(myIntent);
       
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
	}
	
	private void GoToBitacorasView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, BitacoraActivity.class);
        startActivity(myIntent);
       
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
	}
	
	private void GoToMORActivity() 
	{
		Intent myIntent = new Intent(HomeActivity.this, MORActivity.class);
        startActivity(myIntent);
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
	}
	
	private void GoToMainView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(myIntent);
        HomeActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	private void GoToLoginView() 
	{
		Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(myIntent);
        HomeActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	@Override
	public void onBackPressed() 
	{
		usAnteriorUsuario = (UsuarioBO)new SaveObject_Helper().CargarInfoSerializadaUsuarioBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		
		if(usAnteriorUsuario == null)
		{
			super.onBackPressed();
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		}
		else
		{
			if(MainActivity.instance != null) 
			{
		        try 
		        {  
		        	MainActivity.instance.finish();
		        	finish();
		        } 
		        catch (Exception e) 
		        {
		        	Toast.makeText(HomeActivity.this, "Errorcillo por ahi.....", Toast.LENGTH_LONG).show();
		        }
		    }
			else
			{
				finish();	
			}
			
		}
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
					break;
				case UPDATE:
					dmConsumeWebService.ExecWSMethod_Update((PropertyInfo[])arg0[1], (String)arg0[2]);
					bolCerrarSesion = true;
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
			if(!bolCerrarSesion)
			{
				ocOfertasWS = callWebService.ObtenerOfertasUsuarioWS((SoapObject) result);
				
				if(ocOfertasWS == null)
				{
					
					Toast.makeText(HomeActivity.this, "¡Rayos! No tienes ofertas, por ahora...", Toast.LENGTH_LONG).show();
				}
				else
				{
					new SaveObject_Helper().GuardarInfoOfertasBO(ocOfertasWS, getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS));
				}
			}
			else
			{
				EliminarDatosUsuario();
    			Toast.makeText(HomeActivity.this, "Tu sesión ha sido cerrada correctamente. Vuelve pronto!", Toast.LENGTH_SHORT).show();
    			progDailog.dismiss();
        		GoToMainView();
				
			}
			
			initMenuItems();
			progDailog.dismiss();
			
		}
	}

}

