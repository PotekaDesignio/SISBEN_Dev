package com.potekadesignio.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	UsuarioBO usAnteriorUsuario;
	
	private Button btnIniciarSesion;
	private Button btnRegistro;
	private ListView listView ;

	private ArrayList<OfertaCompletaBO> osOfertasAgendadas;
	
	public static MainActivity instance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_main);
		
		initVariables();
		
		if(usAnteriorUsuario == null)
		{
			IniciarApp();	
		}
		else
		{
			Toast.makeText(MainActivity.this, "Bienvenido de nuevo a OfertAPP, " + usAnteriorUsuario.getNombresUsuario(), Toast.LENGTH_LONG).show();
			GoToHomeView();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initVariables() 
	{
		instance = this;
		usAnteriorUsuario = (UsuarioBO)new SaveObject_Helper().CargarInfoSerializadaUsuarioBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_USUARIO)));
		osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
		
		if(osOfertasAgendadas == null)
		{
			osOfertasAgendadas = new ArrayList<OfertaCompletaBO>();
			new SaveObject_Helper().GuardarInfoOfertasAgendadasBO(osOfertasAgendadas, getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS));
		}
	}

	private void IniciarApp()
	{
		initLayout();
		initListenersForViews();
		initMenuItems();
	}
	
	private void initLayout() 
	{
		btnIniciarSesion = (Button)findViewById(R.id.btnIniciarSesion);
		btnRegistro = (Button)findViewById(R.id.btnRegistro);
	}
	
	private void initListenersForViews() 
	{
		btnIniciarSesion.setOnClickListener(new OnClickListener() 
    	{
    	    	public void onClick(View v) 
    	    	{
    	    		if(PK_Utils.isConnected(getApplicationContext(), MainActivity.this))
    	    		{
    	    			GoToLoginView();
    	    		}
    	    		else
    	    		{
    	    			Toast.makeText(MainActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
    	    		}
    	    	}
    	});
		
		btnRegistro.setOnClickListener(new OnClickListener() 
    	{
    	    	public void onClick(View v) 
    	    	{
    	    		if(PK_Utils.isConnected(getApplicationContext(), MainActivity.this))
    	    		{
    	    			GoToRegistroView();    	    		
    	    		}
    	    		else
    	    		{
    	    			Toast.makeText(MainActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
    	    		}
    	    	}
    	});
	}
	
	private void GoToLoginView() 
	{
		Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	private void GoToRegistroView() 
	{
		Intent myIntent = new Intent(MainActivity.this, RegistroActivity.class);
		startActivity(myIntent);
        
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	private void GoToHomeView() 
	{
		Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(myIntent);
        MainActivity.this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    //overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	private void initMenuItems() {

		
        listView = (ListView) findViewById(R.id.list);
        
        ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        ArrayList<Integer> intImageId = new ArrayList<Integer>();
        ArrayList<Integer> intBotonFondoId = new ArrayList<Integer>();
        ArrayList<Integer> intNumOfertas = new ArrayList<Integer>();
        
        
        strTitulo.add("MANUAL DE OFERTAS Y RUTAS"); 
        strSubTitulo.add("Actualizado 26 de junio de 2014"); 
        
        intImageId.add(R.drawable.boton_mor);
        intBotonFondoId.add(R.drawable.fondo_mor);
        intNumOfertas.add(0);
        
        CustomList adapter = new CustomList(MainActivity.this, strTitulo, strSubTitulo, intImageId, intBotonFondoId, intNumOfertas);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {
	        	if(PK_Utils.isConnected(getApplicationContext(), MainActivity.this))
	    		{
	        		Intent myIntent = new Intent(MainActivity.this, MORActivity.class);
		            startActivity(myIntent);
		            
		            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);    	    		
	    		}
	    		else
	    		{
	    			Toast.makeText(MainActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
	    		}
			}
        });
	}
	
	@Override
	public void finish() 
	{
	    super.finish();
	    instance = null;
	}
}
