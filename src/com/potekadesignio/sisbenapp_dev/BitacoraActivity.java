package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;
import com.poteka.ofertapp_dev.R.layout;
import com.poteka.sisbenapp_dev.BitacoraDetalleActivity;
import com.potekadesignio.sisbenapp_dev.BLL.BitacoraBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BitacoraActivity extends Activity 
{
	private ListView listView;
	private ProgressDialog progDailog;
	
	private TextView txtIntroMensaje;
	private UsuarioBO usAnteriorUsuario;
	private ArrayList<BitacoraBO> boBitacorasUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_bitacora);
		
		InitLayout();
		InitVariables();
		initMenuItems();
		
	}

	private void InitLayout() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando historias de Usuario...", true, true);
		listView = (ListView) findViewById(R.id.list);
		txtIntroMensaje = (TextView) findViewById(R.id.txtIntroMensaje);
		listView = (ListView)findViewById(R.id.list);
	}
	
	@SuppressWarnings("unchecked")
	private void InitVariables() 
	{
		usAnteriorUsuario = (UsuarioBO)new SaveObject_Helper().CargarInfoSerializadaUsuarioBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		boBitacorasUsuario = (ArrayList<BitacoraBO>)new SaveObject_Helper().CargarInfoSerializadaBitacorasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
		
		if(usAnteriorUsuario == null)
		{
			progDailog.dismiss();
			Toast.makeText(BitacoraActivity.this, "¡Caramba! Al parecer tu sesión ha sido cerrada. Pero no te preocupes, aquí podrás volver a iniciarla...", Toast.LENGTH_LONG).show();
			GoToLoginView();
		}
		else
		{
			txtIntroMensaje.setText("¡" + usAnteriorUsuario.getNombresUsuario() +   ", Estas son tus historias en OfertAPP!\n" +
					"Puedes consultarlas o crear nuevas...");
		}
			
	}
	
	private void initMenuItems() 
	{
		final ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
        final ArrayList<Integer> intOfertaDetalleID = new ArrayList<Integer>();
		
        strTitulo.add("NUEVA HISTORIA"); 
        strSubTitulo.add("¡Comienza un nuevo relato!");
        intImageId.add(R.drawable.boton_bitacora);
        intBotonFondoId.add(R.drawable.fondo_familia);
        intNumOfertas.add(0);
        intOfertaDetalleID.add(0);
        
        if(boBitacorasUsuario != null)
        {
        	if(boBitacorasUsuario.size() != 0)
            {
            	for(BitacoraBO bitacora : boBitacorasUsuario)
                {
            		strTitulo.add(bitacora.getStrTituloBitacora().toUpperCase());
            		strSubTitulo.add(bitacora.getStrTextoAnotacion());
            		intImageId.add(R.drawable.boton_bitacora);
            		intBotonFondoId.add(R.drawable.fondo_infancia);
            		intNumOfertas.add(0);
            		intOfertaDetalleID.add(bitacora.getIntNumeroBitacora());
                }
            }
        }
        
        CustomList adapter = new CustomList(BitacoraActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {
	        	switch(position)
	        	{
	        		case 0:
	        			GoToBitacoraDetalleActivity();
	        			break;
	        		default:
	        			Intent intent = new Intent(getBaseContext(), BitacoraDetalleActivity.class);
	    	        	intent.putExtra("DETALLE_BITACORA_ID", intOfertaDetalleID.get(+ position).toString());
	    	        	
	    	        	startActivity(intent);
	    	        	
	    	        	overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	        			break;
	        	}
	        	
			}
        });
        progDailog.dismiss();
	}
	
	
	protected void GoToBitacoraDetalleActivity() 
	{
		Intent myIntent = new Intent(BitacoraActivity.this, BitacoraDetalleActivity.class);
        startActivity(myIntent);
        BitacoraActivity.this.finish();
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	private void GoToLoginView() 
	{
		Intent myIntent = new Intent(BitacoraActivity.this, LoginActivity.class);
        startActivity(myIntent);
        BitacoraActivity.this.finish();
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	@SuppressWarnings("unchecked")
	public void onResume()
    {  
		super.onResume();
		boBitacorasUsuario = (ArrayList<BitacoraBO>)new SaveObject_Helper().CargarInfoSerializadaBitacorasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_BITACORAS)));
	    listView.setAdapter(null);
	    initMenuItems();
     }
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
}
