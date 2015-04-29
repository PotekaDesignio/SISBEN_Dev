package com.poteka.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.CustomList;
import com.potekadesignio.sisbenapp_dev.HomeActivity;
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
import android.widget.Toast;

public class HojaOfertaActivity extends Activity 
{
	
	private ListView listView;
	private ProgressDialog progDailog;
	
	private ArrayList<OfertaCompletaBO> osOfertasAgendadas;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_hoja_oferta);
		
		initVariables();
		
	}
	
	@SuppressWarnings("unchecked")
	private void initVariables() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando ofertas para calificar...", true, true);
		osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
		
		if(osOfertasAgendadas.size() != 0)
		{
			initMenuItems();
		}
		else
		{
			Toast.makeText(HojaOfertaActivity.this, "¡Oh-Oh! No tienes ninguna Oferta para Calificar. Agéndate a unas cuantas y aqui te esperamos!", Toast.LENGTH_LONG).show();
			progDailog.dismiss();
			GoToMainView();
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
        final ArrayList<Integer> intOfertaDetalleID = new ArrayList<Integer>();
        
        for(OfertaCompletaBO oferta :  osOfertasAgendadas)
        {
    		strTituloTotales.add(oferta.getOfe_NombreOferta().toUpperCase());
        	
        	if(!strTitulo.contains(oferta.getOfe_NombreOferta().toUpperCase()))
        	{
        		strTitulo.add(oferta.getOfe_NombreOfertaCorto().toUpperCase());
        		intOfertaDetalleID.add(oferta.getOfd_DetalleOfertaId());
        		strSubTitulo.add(oferta.getOfe_NombreOferente().toUpperCase());
        		intNumOfertas.add(0);
        		
        		switch(oferta.getDim_DimensionId())
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
        
        CustomList adapter = new CustomList(HojaOfertaActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	Intent intent = new Intent(getBaseContext(), CalificarOfertaActivity.class);
	        	intent.putExtra("DETALLE_OFERTA_ID", intOfertaDetalleID.get(+ position).toString());
	        	
	        	startActivity(intent);
	        	
	        	overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	        	
        	}
        });
		
        progDailog.dismiss();
        
	}
	
	private void GoToMainView() 
	{
		Intent myIntent = new Intent(HojaOfertaActivity.this, HomeActivity.class);
        startActivity(myIntent);
        HojaOfertaActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
