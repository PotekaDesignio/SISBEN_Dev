package com.poteka.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import com.google.android.gms.internal.bo;
import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BitacoraActivity;
import com.potekadesignio.sisbenapp_dev.CustomList;
import com.potekadesignio.sisbenapp_dev.HomeActivity;
import com.potekadesignio.sisbenapp_dev.MORActivity;
import com.potekadesignio.sisbenapp_dev.MainActivity;
import com.potekadesignio.sisbenapp_dev.PriorizacionOfertaActivity;
import com.potekadesignio.sisbenapp_dev.BLL.BitacoraBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BitacoraDetalleActivity extends Activity 
{
	private EditText txtTitulo;
	private EditText txtTextoBitacora;
	
	private ListView listView;
	private ArrayList<BitacoraBO> boBitacorasUsuario;
	private BitacoraBO buBitacoraSeleccion;
	private String intBitacoraDetalleID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_bitacora_detalle);
		
		Initlayout();
		initVariables();
		initMenuItems();
		CargarBitacora();
	}
	
	private void Initlayout() 
	{
		txtTitulo = (EditText)findViewById(R.id.txtTitulo);
		txtTextoBitacora = (EditText)findViewById(R.id.txtTextoBitacora);
		listView = (ListView)findViewById(R.id.list);
		
	}

	@SuppressWarnings("unchecked")
	private void initVariables() 
	{
		Bundle extras = getIntent().getExtras();
		
		boBitacorasUsuario = (ArrayList<BitacoraBO>)new SaveObject_Helper().CargarInfoSerializadaBitacorasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
		
		if (extras != null) 
		{
			intBitacoraDetalleID = extras.getString("DETALLE_BITACORA_ID");
			
			for(BitacoraBO bitacora :  boBitacorasUsuario)
	        {
				if(Integer.parseInt(intBitacoraDetalleID) == bitacora.getIntNumeroBitacora())
				{
					buBitacoraSeleccion = bitacora;
					break;
				}
	        }
		
		}
			
			
	}

	private void initMenuItems() 
	{

		listView = (ListView) findViewById(R.id.list);
        
        ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
        
        
        strTitulo.add("GUARDAR"); 
        strSubTitulo.add("Guarda ésta historia. ¡Lo merece!"); 
        
        intImageId.add(R.drawable.boton_mor);
        intBotonFondoId.add(R.drawable.fondo_justicia);
        intNumOfertas.add(0);
        
        CustomList adapter = new CustomList(BitacoraDetalleActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {
	        	try 
	        	{
	        		BitacoraBO buEditada = new BitacoraBO();
		        	
		        	if(buBitacoraSeleccion != null)
		    		{
		        		boBitacorasUsuario.remove(buBitacoraSeleccion);
		        		
		    		}
		        	else if(boBitacorasUsuario == null)
		        	{
		        		boBitacorasUsuario = new ArrayList<BitacoraBO>();
		        	}
		        	
		        	buEditada.setIntNumeroBitacora(boBitacorasUsuario.size()+1);
	        		buEditada.setStrTituloBitacora(txtTitulo.getText().toString());
	        		buEditada.setStrTextoAnotacion(txtTextoBitacora.getText().toString());
	        		
	        		boBitacorasUsuario.add(buEditada);
					
	        		new SaveObject_Helper().GuardarInfoBitacorasBO(boBitacorasUsuario, getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS));
	        		
	        		Toast.makeText(BitacoraDetalleActivity.this, "¡Excelente! Hemos guardado un capítulo más de tu vida. ¡Vuelve y escribe cuantos quieras!", Toast.LENGTH_LONG).show();
	        		GoToBitacoraView();
	        		
				} 
	        	catch (Exception e) 
	        	{
	        		Toast.makeText(BitacoraDetalleActivity.this, "¡Rayos! Se presentó un problema al guardar tu historia...", Toast.LENGTH_LONG).show();	
				}
	        	
	        	
			}
        });
	}
	
	private void GoToBitacoraView() 
	{
		Intent myIntent = new Intent(BitacoraDetalleActivity.this, BitacoraActivity.class);
		getIntent().removeExtra("DETALLE_BITACORA_ID");
		
        startActivity(myIntent);
        BitacoraDetalleActivity.this.finish();
        
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	private void CargarBitacora() 
	{
		if(buBitacoraSeleccion != null)
		{
			txtTitulo.setText(buBitacoraSeleccion.getStrTituloBitacora());
			txtTextoBitacora.setText(buBitacoraSeleccion.getStrTextoAnotacion());
		}
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
