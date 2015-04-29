package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.poteka.ofertapp_dev.R;
import com.poteka.sisbenapp_dev.HojaOfertaActivity;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.utils.HttpAsyncTask;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MORActivity extends Activity implements AsyncResponse
{
	
	private ListView listView;
	private OfertaSimpleBO osOfertasMOR[];
	private HttpAsyncTask hatConsultaMOR;
	
	private ProgressDialog progDailog;
	
	private String strMor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_mor);
		
		initDatosMOR();
	}
	
	private void initDatosMOR() 
	{
		osOfertasMOR = (OfertaSimpleBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasMOROfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_MOR)));
		
		if(osOfertasMOR == null)
		{
			if(PK_Utils.isConnected(getApplicationContext(), MORActivity.this))
			{
				progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando Manual de Ofertas y Rutas...", true, true);
				hatConsultaMOR = new HttpAsyncTask();
				hatConsultaMOR.delegate = this;
				
				hatConsultaMOR.execute(getString(R.string.HTTP_MOR_DATOS_ABIERTOS));
			}
			else
    		{
    			Toast.makeText(MORActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
    			progDailog.dismiss();
    			GoToMainView();
    		}
		}
		else
		{
			initMenuItems();
		}
	}

	private void initMenuItems() 
	{
        listView = (ListView) findViewById(R.id.list);
        
        final ArrayList<String> strTitulo = new ArrayList<String>();
        final ArrayList<String> strTituloTotales = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
        
        for(int i = 0; i < osOfertasMOR.length; i++)
        {
        	strTituloTotales.add(osOfertasMOR[i].getDimension().toUpperCase());
        	
        	if(!strTitulo.contains(osOfertasMOR[i].getDimension().toUpperCase()))
        	{
        		strTitulo.add(osOfertasMOR[i].getDimension().toUpperCase());
        		
        		switch(osOfertasMOR[i].getDimension())
        		{
        			case "ingresos y trabajo":
        				intImageId.add(R.drawable.logo_trabajo);
        				intBotonFondoId.add(R.drawable.fondo_trabajo);
        				break;
        			case "identificacion":
        				intImageId.add(R.drawable.logo_identificacion);
        				intBotonFondoId.add(R.drawable.fondo_identificacion);
        				break;
        			case "educacion":
        				intImageId.add(R.drawable.logo_educacion);
        				intBotonFondoId.add(R.drawable.fondo_educacion);
        				break;
        			case "salud":
        				intImageId.add(R.drawable.logo_salud);
        				intBotonFondoId.add(R.drawable.fondo_salud);
        				break;
        			case "nutricion":
        				intImageId.add(R.drawable.logo_nutricion);
        				intBotonFondoId.add(R.drawable.fondo_nutricion);
        				break;
        			case "habitabilidad":
        				intImageId.add(R.drawable.logo_habitabilidad);
        				intBotonFondoId.add(R.drawable.fondo_habitabilidad);
        				break;
        			case "dinamica familiar":
        				intImageId.add(R.drawable.logo_familia);
        				intBotonFondoId.add(R.drawable.fondo_familia);
        				break;
        			case "bancarizacion":
        				intImageId.add(R.drawable.logo_banca);
        				intBotonFondoId.add(R.drawable.fondo_banca);
        				break;
        			case "justicia":
        				intImageId.add(R.drawable.logo_justicia);
        				intBotonFondoId.add(R.drawable.fondo_justicia);
        				break;
        			case "primera infancia":
        				intImageId.add(R.drawable.logo_infancia);
        				intBotonFondoId.add(R.drawable.fondo_infancia);
        				break;
        			case "comunitario":
        				intImageId.add(R.drawable.logo_comunitario);
        				intBotonFondoId.add(R.drawable.fondo_comunitario);
        				break;
    				default:
    					break;
        		}
        	}
        }
        
        for(int i = 0; i < strTitulo.size(); i++)
        {
        	//int intCantidadOfertas = Collections.frequency(strTituloTotales, strTitulo.get(i));
        	//intNumOfertas.add(intCantidadOfertas);
        	intNumOfertas.add(0);
        	
        	/*
        	if(intCantidadOfertas > 1)
        		strSubTitulo.add("Hay " + intCantidadOfertas + " ofertas publicadas!");
        	else
        		strSubTitulo.add("Hay una oferta publicada!");
        		*/
        	
        	strSubTitulo.add("¡Hay información publicada!");
        }
        
        CustomList adapter = new CustomList(MORActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	//Toast.makeText(MORActivity.this, "En desarrollo: " + strTitulo.get(+ position), Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent(getBaseContext(), OfertaDetalleMORActivity.class);
	        	
	        	intent.putExtra("NOMBRE_DIMENSION", strTitulo.get(+ position));
	        	startActivity(intent);
	        	
	        	overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
        });
		
	}
	
	private void GoToMainView() 
	{
		Intent myIntent = new Intent(MORActivity.this, HomeActivity.class);
        startActivity(myIntent);
        MORActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
		strMor = result.toString();
		try 
		{
			osOfertasMOR = new PK_Utils().LeerOfertasMOR(strMor);
			initMenuItems();
			new SaveObject_Helper().GuardarInfoMOROfertasBO(osOfertasMOR, getString(R.string.RUTA_NOMBRE_DATOS_MOR));
			progDailog.dismiss();
		} 
		catch (Exception e) 
		{
			Toast.makeText(MORActivity.this, "Error leyendo MOR: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	
}
