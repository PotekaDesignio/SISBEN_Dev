package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.poteka.ofertapp_dev.R;
import com.poteka.ofertapp_dev.R.anim;
import com.poteka.ofertapp_dev.R.drawable;
import com.poteka.ofertapp_dev.R.id;
import com.poteka.ofertapp_dev.R.layout;
import com.poteka.ofertapp_dev.R.string;
import com.potekadesignio.sisbenapp_dev.BLL.DimensionBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PriorizacionOfertaActivity extends Activity 
{
	private UsuarioBO usAnteriorUsuario = null;
	private DimensionBO[] diDimensiones;
	
	private ListView listView;
	private ProgressDialog progDailog;
	
	private TextView txtIntroMensaje;
	private String strNombreDimension;
	
	private String WSDL_TARGET_NAMESPACE; 
	private String SOAP_ADDRESS; 
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private OfertaAPP_DataManager callWebService;
	public boolean error;
	public enum Operacion { SELECT, INSERT, UPDATE, DELETE }

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_priorizacion_oferta);
		
		InitLayout();
		InitVariables();
		
	}
	
	public void InitVariables()
	{
		WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
		SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
		
		usAnteriorUsuario = (UsuarioBO)new SaveObject_Helper().CargarInfoSerializadaUsuarioBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		diDimensiones = (DimensionBO[])new SaveObject_Helper().CargarInfoSerializadaDimensionBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_DIMENSIONES)));
		
		
		//txtIntroMensaje.setText("Selecciona la dimensión sobre la cual deseas generar una solicitud a la ANSPE.");
		txtIntroMensaje.setText("Cuéntanos qué dimensión te parece importante para gestionar en tu Municipio.");
		
		if(diDimensiones == null)
		{
			InitVariablesWS();
			CargarDatosDimensionesWS();
		}
		else
		{
			initMenuItems();
		}
			
	}
	
	private void InitVariablesWS() 
	{
		callWebService = new OfertaAPP_DataManager();
		dmConsumeWebService = new OfertAPP_DataConnectionInterface();
		dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
		dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
	}

	private void CargarDatosDimensionesWS() 
	{
		PropertyInfo piParameters[] = null;
		
		dmConsumeWebService.wsDataManager_OperationName("DescargarInfoDimensionXML");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "DescargarInfoDimensionXML");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(PriorizacionOfertaActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}		
	}
	
	private void SolicitarOfertaPrioritariaWS(int pDimensionID) 
	{
		PropertyInfo piParameters[];
		piParameters = new PropertyInfo[2];
        
		piParameters[0] = new PropertyInfo();
		piParameters[0].setName("pUsuarioId");
		piParameters[0].setValue(usAnteriorUsuario.getUsuarioId());
		
		piParameters[0].setType(Integer.class);
		
		piParameters[1] = new PropertyInfo();
		piParameters[1].setName("pDimensionId");
		piParameters[1].setValue(pDimensionID);
		
		piParameters[1].setType(Integer.class);
		
		dmConsumeWebService.wsDataManager_OperationName("SolicitarOfertaPrioritaria");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.INSERT, piParameters, "SolicitarOfertaPrioritaria");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(PriorizacionOfertaActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}		
	}
	
	private void InitLayout() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando información...", true, true);
		listView = (ListView) findViewById(R.id.list);
		txtIntroMensaje = (TextView) findViewById(R.id.txtIntroMensaje);
	}
	
	private void initMenuItems() 
	{
        final ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
		
		
        for(int i = 0; i < diDimensiones.length; i++)
        {
        		strTitulo.add(diDimensiones[i].getDim_NombreDimension().toUpperCase());
        		strSubTitulo.add("¡Prioríza ésta dimensión!");
        		intNumOfertas.add(0);
        		
        		switch(diDimensiones[i].getDim_DimensionId())
        		{
	        		case 1:
	    				intImageId.add(R.drawable.logo_identificacion);
	    				intBotonFondoId.add(R.drawable.fondo_identificacion);
	    				break;
        			case 2:
        				intImageId.add(R.drawable.logo_trabajo);
        				intBotonFondoId.add(R.drawable.fondo_trabajo);
        				break;
        			case 3:
        				intImageId.add(R.drawable.logo_educacion);
        				intBotonFondoId.add(R.drawable.fondo_educacion);
        				break;
        			case 4:
        				intImageId.add(R.drawable.logo_salud);
        				intBotonFondoId.add(R.drawable.fondo_salud);
        				break;
        			case 5:
        				intImageId.add(R.drawable.logo_nutricion);
        				intBotonFondoId.add(R.drawable.fondo_nutricion);
        				break;
        			case 6:
        				intImageId.add(R.drawable.logo_habitabilidad);
        				intBotonFondoId.add(R.drawable.fondo_habitabilidad);
        				break;
        			case 7:
        				intImageId.add(R.drawable.logo_familia);
        				intBotonFondoId.add(R.drawable.fondo_familia);
        				break;
        			case 8:
        				intImageId.add(R.drawable.logo_banca);
        				intBotonFondoId.add(R.drawable.fondo_banca);
        				break;
        			case 9:
        				intImageId.add(R.drawable.logo_justicia);
        				intBotonFondoId.add(R.drawable.fondo_justicia);
        				break;
        			case 10:
        				intImageId.add(R.drawable.logo_infancia);
        				intBotonFondoId.add(R.drawable.fondo_infancia);
        				break;
        			case 11:
        				intImageId.add(R.drawable.logo_comunitario);
        				intBotonFondoId.add(R.drawable.fondo_comunitario);
        				break;
    				default:
    					break;
        		}
        }
        		
        CustomList adapter = new CustomList(PriorizacionOfertaActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	strNombreDimension = strTitulo.get(+ position);
	        	
	        	new AlertDialog.Builder(PriorizacionOfertaActivity.this)
	        	//.setTitle("Priorización de Oferta").setMessage("Vas a solicitar Oferta Prioritaria para la dimensión\n" + strNombreDimension + "\n¿Confirmas tu selección?")
	        	.setTitle("Encuesta de Oferta").setMessage("Vas a votar por la Dimensión\n" + strNombreDimension + "\n¿Confirmas tu selección?")
	        	.setIcon(android.R.drawable.ic_dialog_alert)
	        	.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
	        	{
	        	    public void onClick(DialogInterface dialog, int whichButton) 
	        	    {
	        	    	int intDimensionID = -1;
	        	    	try 
	        	    	{
	        	    		for(int i = 0; i < diDimensiones.length; i++)
	        	    		{
	        	    			if(diDimensiones[i].getDim_NombreDimension().toUpperCase().equals(strNombreDimension))
	        	    			{
	        	    				intDimensionID = diDimensiones[i].getDim_DimensionId();
	        	    				break;
	        	    			}
	        	    		}
	        	    		
	        	    		if(intDimensionID != -1)
	        	    		{
	        	    			progDailog = ProgressDialog.show(PriorizacionOfertaActivity.this,"OfertAPP", "Enviando solicitud...", true, true);
	        	    			InitVariablesWS();
	        	    			SolicitarOfertaPrioritariaWS(intDimensionID);
	        	    		}
	        	    		else
	        	    		{
	        	    			Toast.makeText(PriorizacionOfertaActivity.this, "¿Y ésto que fue? ¡Esa Dimensión no existe!", Toast.LENGTH_LONG).show();
	        	    		}
						} 
	        	    	catch (Exception e) 
	        	    	{
	        	    		Toast.makeText(PriorizacionOfertaActivity.this, "¡Yaay! Se ha presentado un error..." + e.getMessage(), Toast.LENGTH_LONG).show();
	        	    		progDailog.dismiss();
						}
	        	    }})
	        	    .setNegativeButton(android.R.string.no, null).show();
	        	
				
			}
        });
        progDailog.dismiss();
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
						dmConsumeWebService.ExecWSMethod_Insert((PropertyInfo[])arg0[1], (String)arg0[2]);
						error = false;
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						error = true;
					}
					return null;
				case UPDATE:
					return null;
				case DELETE:
					return null;
				default:
					return null;
	    	}
		} 
		@Override
		protected void onPostExecute(Object result) 
		{
			if(result != null)
			{
				diDimensiones = callWebService.ObtenerDimensiones((SoapObject) result);
				
				if(diDimensiones == null)
				{
					Toast.makeText(PriorizacionOfertaActivity.this, "Rayos! No hay ninguna dimensión está activa...", Toast.LENGTH_LONG).show();
				}
				else
				{
					new SaveObject_Helper().GuardarInfoDimensionesBO(diDimensiones, getString(R.string.RUTA_NOMBRE_DATOS_DIMENSIONES));
				}
				
				initMenuItems();
			}
			else if(!error)
			{
				//Toast.makeText(PriorizacionOfertaActivity.this, "¡Excelente! Tu solicitud ha sido enviada.", Toast.LENGTH_LONG).show();
				Toast.makeText(PriorizacionOfertaActivity.this, "¡Excelente! Tu opinión ha sido enviada.", Toast.LENGTH_LONG).show();
				progDailog.dismiss();
				GoToMainView();
			}
			else
			{
				Toast.makeText(PriorizacionOfertaActivity.this, "¡Yaay! Se ha presentado un error...", Toast.LENGTH_LONG).show();
			}
			progDailog.dismiss();
			
		}
	}
	
	private void GoToMainView() 
	{
		Intent myIntent = new Intent(PriorizacionOfertaActivity.this, HomeActivity.class);
        startActivity(myIntent);
        PriorizacionOfertaActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
}
