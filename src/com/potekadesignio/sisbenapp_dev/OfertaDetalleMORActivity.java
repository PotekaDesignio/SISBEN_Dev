package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.poteka.ofertapp_dev.R;
import com.poteka.ofertapp_dev.R.layout;
import com.poteka.sisbenapp_dev.OfertaSeleccionMORActivity;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class OfertaDetalleMORActivity extends Activity 
{
	private ListView listView;
	private ProgressDialog progDailog;
	private TextView txtIntroMensaje;
	
	private OfertaSimpleBO osOfertasMOR[];
	private String strNombreDimension;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.activity_oferta_detalle_mor);
		
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
			osOfertasMOR = (OfertaSimpleBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasMOROfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_MOR)));
			strNombreDimension = extras.getString("NOMBRE_DIMENSION");
			//txtIntroMensaje.setText("Estas son tus Ofertas disponibles para la Dimensión " + strNombreDimension);
			txtIntroMensaje.setText("Esta es la información disponible para la Dimensión " + strNombreDimension);
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
        final ArrayList<String> strOfertaDetalleID = new ArrayList<String>();
        
        for(int i = 0; i < osOfertasMOR.length; i++)
        {
        	if(osOfertasMOR[i].getDimension().toUpperCase().equals(strNombreDimension))
        	{
        		strTituloTotales.add(osOfertasMOR[i].getDimension().toUpperCase());
            	
            	if(!strTitulo.contains(osOfertasMOR[i].getDimension().toUpperCase()))
            	{
            		strTitulo.add(osOfertasMOR[i].getOferta().toUpperCase());
            		strSubTitulo.add(osOfertasMOR[i].getEntidad().toUpperCase());
            		strOfertaDetalleID.add(osOfertasMOR[i].getNumero());
            		
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
        }
        
        for(int i = 0; i < strTitulo.size(); i++)
        {
        	int intCantidadOfertas = Collections.frequency(strTituloTotales, strTitulo.get(i));
        	intNumOfertas.add(intCantidadOfertas);	
        }
        
        CustomList adapter = new CustomList(OfertaDetalleMORActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	        {
	        	Intent intent = new Intent(getBaseContext(), OfertaSeleccionMORActivity.class);
	        	intent.putExtra("DETALLE_OFERTA_ID", strOfertaDetalleID.get(+ position).toString());
	        	
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
