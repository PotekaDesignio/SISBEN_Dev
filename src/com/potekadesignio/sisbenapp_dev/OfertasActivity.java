package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class OfertasActivity extends Activity 
{

	private ListView listView;
	private ProgressDialog progDailog;
	
	private OfertaCompletaBO osOfertasUsuario[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_ofertas);
		
		initVariables();
		initMenuItems();
	}

	private void initVariables() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando ofertas disponibles...", true, true);
		osOfertasUsuario = (OfertaCompletaBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS)));
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
        
        for(int i = 0; i < osOfertasUsuario.length; i++)
        {
    		strTituloTotales.add(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase());
        	
        	if(!strTitulo.contains(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase()))
        	{
        		strTitulo.add(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase());
        		
        		switch(osOfertasUsuario[i].getDim_DimensionId())
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
        }
        
        for(int i = 0; i < strTitulo.size(); i++)
        {
        	/*
        	int intCantidadOfertas = Collections.frequency(strTituloTotales, strTitulo.get(i));
        	intNumOfertas.add(intCantidadOfertas);
        	
        	if(intCantidadOfertas > 1)
        		strSubTitulo.add("Hay " + intCantidadOfertas + " ofertas publicadas!");
        	else
        		strSubTitulo.add("Hay una oferta publicada!");
        	*/
        	intNumOfertas.add(0);
        	strSubTitulo.add("¡Hay información publicada!");
        }

        CustomList adapter = new CustomList(OfertasActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	Intent intent = new Intent(getBaseContext(), OfertaCompletaDetalleActivity.class);
	        	
	        	intent.putExtra("NOMBRE_DIMENSION", strTitulo.get(+ position));
	        	startActivity(intent);
	        	
	        	overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	        	
        	}
        });
		
        progDailog.dismiss();
        
	}
	
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
