package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.poteka.ofertapp_dev.R;
import com.poteka.ofertapp_dev.R.anim;
import com.poteka.ofertapp_dev.R.drawable;
import com.poteka.ofertapp_dev.R.id;
import com.poteka.ofertapp_dev.R.layout;
import com.poteka.ofertapp_dev.R.string;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OfertaCompletaDetalleActivity extends Activity 
{
	
	private ListView listView;
	private ProgressDialog progDailog;
	private TextView txtIntroMensaje; 
	
	
	private OfertaCompletaBO osOfertasUsuario[];
	private String strNombreDimension;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_oferta_completa_detalle);
		
		initLayout();
		initVariables();
		initMenuItems();
		
	}
	
	private void initLayout() 
	{
		txtIntroMensaje = (TextView)findViewById(R.id.txtIntroMensaje);
	}

	private void initVariables() 
	{
		Bundle extras = getIntent().getExtras();
		
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando ofertas disponibles...", true, true);
		
		if (extras != null) 
		{
			osOfertasUsuario = (OfertaCompletaBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS)));
			strNombreDimension = extras.getString("NOMBRE_DIMENSION");
			txtIntroMensaje.setText("Estas son tus Ofertas disponibles para la Dimensión " + strNombreDimension);
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
        
        for(int i = 0; i < osOfertasUsuario.length; i++)
        {
        	
        	if(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase().equals(strNombreDimension))
        	{
        		
        		if(!VerificarOfertaExistente(intOfertaDetalleID, osOfertasUsuario[i].getOfd_DetalleOfertaId()))
        		{
        			strTituloTotales.add(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase());
                	
                	if(!strTitulo.contains(osOfertasUsuario[i].getDim_NombreDimension().toUpperCase()))
                	{
                		strTitulo.add(osOfertasUsuario[i].getOfe_NombreOferta().toUpperCase());
                		strSubTitulo.add(osOfertasUsuario[i].getOfe_NombreOferente().toUpperCase());
                		            		
                			intOfertaDetalleID.add(osOfertasUsuario[i].getOfd_DetalleOfertaId());
                    		
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
        	}
        }
        
        for(int i = 0; i < strTitulo.size(); i++)
        {
        	int intCantidadOfertas = Collections.frequency(strTituloTotales, strTitulo.get(i));
        	intNumOfertas.add(intCantidadOfertas);	
        }
        
        CustomList adapter = new CustomList(OfertaCompletaDetalleActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	Intent intent = new Intent(getBaseContext(), OfertaSeleccionActivity.class);
	        	intent.putExtra("DETALLE_OFERTA_ID", intOfertaDetalleID.get(+ position).toString());
	        	
	        	startActivity(intent);
	        	
	        	overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
        });
		
        progDailog.dismiss();
	}
	
	public boolean VerificarOfertaExistente(ArrayList<Integer> intOfertaDetalleID, int Ofd_DetalleOfertaId)
	{
		for(int ofertaId : intOfertaDetalleID)
		{
			if(ofertaId == Ofd_DetalleOfertaId)
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	
}
