package com.poteka.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.CalificacionList;
import com.potekadesignio.sisbenapp_dev.HomeActivity;
import com.potekadesignio.sisbenapp_dev.OfertaSeleccionActivity;
import com.potekadesignio.sisbenapp_dev.RegistroActivity;
import com.potekadesignio.sisbenapp_dev.BLL.CriterioEvaluacionBO;
import com.potekadesignio.sisbenapp_dev.BLL.DepartamentoBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.DAL.OfertAPP_DataConnectionInterface;
import com.potekadesignio.sisbenapp_dev.DAL.OfertaAPP_DataManager;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalificarOfertaActivity extends Activity 
{
	private ListView listView;
	private ProgressDialog progDailog;
	
	private RelativeLayout rlyInfoEventoContenidoDesplegable;
	private RelativeLayout rlyCalificaExperienciaContenidoDesplegable;
	private RelativeLayout rlyAgregarFotosContenidoDesplegable;
	
	private CriterioEvaluacionBO coCriteriosEvaluacion[];
	
	private ImageView imgFondoDetalleDimension;
	private ImageView imgFondoDimension;
	private ImageView imgDimension;
	private ImageView imgFondoEncabezadoDescripcion;
	private ImageView imgInfoEvento; 
	private ImageView imgCalificaExperiencia;
	private ImageView imgAgregarFotos;
	private ImageView imgGuardarInfo;
	
	private TextView txtTitulo;
	private TextView txtSubTitulo;
	private TextView txtTextoSuperiorOferta;
	private TextView txtDescripcionOferta;
	private TextView txtLogrosOferta;
	
	private ArrayList<OfertaCompletaBO> osOfertasAgendadas;
	private OfertaCompletaBO osOfertaSeleccion;
	private String strOfertaDetalleID;
	
	private String WSDL_TARGET_NAMESPACE; 
	private String SOAP_ADDRESS; 
	private OfertAPP_DataConnectionInterface dmConsumeWebService;
	private OfertaAPP_DataManager callWebService;
	private enum Operacion { SELECT, INSERT, UPDATE, DELETE }
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_calificar_oferta);
		
		Initlayout();
		InitVariables();
		CargarDatosOferta();
	}
	
	private void Initlayout() 
	{
		listView = (ListView) findViewById(R.id.list);
		
		imgFondoDetalleDimension = (ImageView) findViewById(R.id.imgFondoDetalleDimension);
		imgFondoDimension = (ImageView) findViewById(R.id.imgFondoDimension);
		imgDimension = (ImageView) findViewById(R.id.imgDimension);
		imgFondoEncabezadoDescripcion = (ImageView) findViewById(R.id.imgFondoEncabezadoDescripcion);
		imgInfoEvento = (ImageView) findViewById(R.id.imgInfoEvento);
		imgCalificaExperiencia = (ImageView) findViewById(R.id.imgCalificaExperiencia);
		imgAgregarFotos = (ImageView) findViewById(R.id.imgAgregarFotos);
		imgGuardarInfo = (ImageView) findViewById(R.id.imgGuardarInfo);
		
		txtTitulo = (TextView) findViewById(R.id.txtTitulo);
		txtSubTitulo = (TextView) findViewById(R.id.txtSubTitulo); 
		txtTextoSuperiorOferta = (TextView) findViewById(R.id.txtTextoSuperiorOferta);
		txtDescripcionOferta = (TextView) findViewById(R.id.txtDescripcionOferta);
		txtLogrosOferta = (TextView) findViewById(R.id.txtLogrosOferta);
		
		rlyInfoEventoContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyInfoEventoContenidoDesplegable);
		rlyCalificaExperienciaContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyCalificaExperienciaContenidoDesplegable);
		rlyAgregarFotosContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyAgregarFotosContenidoDesplegable);
		
		
		rlyInfoEventoContenidoDesplegable.setVisibility(View.GONE);
		rlyCalificaExperienciaContenidoDesplegable.setVisibility(View.GONE);
		rlyAgregarFotosContenidoDesplegable.setVisibility(View.GONE);
		
		listView.setOnTouchListener(new OnTouchListener() {
		    // Setting on Touch Listener for handling the touch inside ScrollView
		   @Override
			public boolean onTouch(View v, MotionEvent event) {
			   v.getParent().requestDisallowInterceptTouchEvent(true);
			    return false;
			}
		});
		
		
	}
	
	private void InitVariables() 
	{
		progDailog = ProgressDialog.show(this,"OfertAPP", "Cargando items para calificar...", true, true);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) 
		{
			
			WSDL_TARGET_NAMESPACE = getString(R.string.WSDL_TARGET_NAMESPACE);
			SOAP_ADDRESS = getString(R.string.SOAP_ADDRESS);
			
			callWebService = new OfertaAPP_DataManager();
			dmConsumeWebService = new OfertAPP_DataConnectionInterface();
			dmConsumeWebService.wsDataManager_SOAP_ADDRESS(SOAP_ADDRESS);
			dmConsumeWebService.wsDataManager_WSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
			
			if(PK_Utils.isConnected(getApplicationContext(), CalificarOfertaActivity.this))
			{
				strOfertaDetalleID = extras.getString("DETALLE_OFERTA_ID");
				CargarDatosCalificacionOfertaWS();
				CargarDatosOfertaCalificar(strOfertaDetalleID);
			}
			else
			{
				progDailog.dismiss();
				Toast.makeText(CalificarOfertaActivity.this, "¿Uh? Al parecer no estás conectado a Internet. Por favor, verifíca tu conexión e inténtalo de nuevo.", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}
	
	private void CargarDatosCalificacionOfertaWS() 
	{
		PropertyInfo piParameters[] = null;
		
		dmConsumeWebService.wsDataManager_OperationName("ObtenerCriteriosEvaluacionOferta");
		
		try 
		{	
			new WSDataBaseTask().execute(Operacion.SELECT, piParameters, "ObtenerCriteriosEvaluacionOferta");
		} 
		catch (Exception ex) 
		{
			Toast.makeText(CalificarOfertaActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@SuppressWarnings("unchecked")
	private void CargarDatosOfertaCalificar(String pOfertaDetalleID) 
	{
		osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
		
		for(OfertaCompletaBO oferta :  osOfertasAgendadas)
        {
			if(Integer.parseInt(pOfertaDetalleID) == oferta.getOfd_DetalleOfertaId())
			{
				osOfertaSeleccion = oferta;
				break;
			}
        }
		
	}

	private void CargarDatosOferta() 
	{
		switch(osOfertaSeleccion.getDim_DimensionId())
		{
			case 1:
				txtTitulo.setText("IDENTIFICACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_identificacion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_identificacion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_identificacion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_identificacion);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
								
				break;
			case 2:
				txtTitulo.setText("TRABAJO");
				
				imgDimension.setBackgroundResource(R.drawable.logo_trabajo);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_trabajo);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_trabajo);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_trabajo);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			
			case 3:
				txtTitulo.setText("EDUCACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_educacion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_educacion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_educacion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_educacion);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 4:
				txtTitulo.setText("SALUD");
				
				imgDimension.setBackgroundResource(R.drawable.logo_salud);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_salud);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_salud);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_salud);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 5:
				txtTitulo.setText("NUTRICION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_nutricion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_nutricion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_nutricion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_nutricion);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 6:
				txtTitulo.setText("HABITABILIDAD");
				
				imgDimension.setBackgroundResource(R.drawable.logo_habitabilidad);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_habitabilidad);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_habitabilidad);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_habitabilidad);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);				
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
								
				break;
			case 7:
				txtTitulo.setText("FAMILIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_familia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_familia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_familia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_familia);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));				
				
				break;
			case 8:
				txtTitulo.setText("BANCARIZACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_banca);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_banca);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_banca);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_banca);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 9:
				txtTitulo.setText("JUSTICIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_justicia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_justicia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_justicia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_justicia);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 10:
				txtTitulo.setText("INFANCIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_infancia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_infancia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_infancia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_infancia);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_infancia);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_infancia);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_infancia);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_infancia);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 11:
				txtTitulo.setText("COMUNITARIO");
				
				imgDimension.setBackgroundResource(R.drawable.logo_comunitario);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_comunitario);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_comunitario);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_comunitario);
				imgInfoEvento.setBackgroundResource(R.drawable.fondo_comunitario);
				imgCalificaExperiencia.setBackgroundResource(R.drawable.fondo_comunitario);
				imgAgregarFotos.setBackgroundResource(R.drawable.fondo_comunitario);
				imgGuardarInfo.setBackgroundResource(R.drawable.fondo_comunitario);
				
				rlyInfoEventoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyCalificaExperienciaContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyAgregarFotosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			default:
				break;
		}
		
		txtSubTitulo.setText(osOfertaSeleccion.getOfe_NombreOferta());
		txtTextoSuperiorOferta.setText(osOfertaSeleccion.getOra_Descripcion() + " - " + osOfertaSeleccion.getOfe_NombreOferente() + " - " + osOfertaSeleccion.getTpo_Descripcion());
		txtDescripcionOferta.setText(osOfertaSeleccion.getOfe_NombreOfertaCorto().toUpperCase());
		txtLogrosOferta.setText("Logro(s): " + osOfertaSeleccion.getLog_LogroId());
		
	}
	
	public void MostrarOcultar(View v)
	{
		String name = v.getTag().toString();
		int resID = getResources().getIdentifier(name + "ContenidoDesplegable", "id", getPackageName());
		final RelativeLayout test = (RelativeLayout) findViewById(resID);
		
		if(test.isShown())
		{
			PK_Utils.slide_up(CalificarOfertaActivity.this, test);
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() 
			{
				public void run() 
				{
					test.setVisibility(View.GONE);
				}
			}, 200);
		}
		else
		{
			test.setVisibility(View.VISIBLE);
			PK_Utils.slide_down(this, test);
		}
	}
	
	public void btnCalificarOferta_click(View v)
	{
		
	}
		
	private void initRatingItems() 
	{
        final ArrayList<String> strTitulo = new ArrayList<String>();
        ArrayList<String> strSubTitulo = new ArrayList<String>();
        
        for(int i = 0; i < coCriteriosEvaluacion.length; i++)
        {
        	strTitulo.add(coCriteriosEvaluacion[i].getCie_Descripcion().toUpperCase());
        	strSubTitulo.add(coCriteriosEvaluacion[i].getCie_TextoGuia());
        }
        		
        CalificacionList adapter = new CalificacionList(CalificarOfertaActivity.this, strTitulo, strSubTitulo);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
	        @Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {
	        	switch(position)
	        	{
	        		case 0:
	        			break;
	        		case 1:
	        			break;
	        		case 2:
	        			break;
	        		case 3:
	        			break;
	        		case 4:
		        		break;
	        		default:
	        	}
	        	
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
			if(result != null)
			{
				coCriteriosEvaluacion = callWebService.ObtenerCriteriosEvaluacion((SoapObject) result);
				initRatingItems();
			}
			else
			{
				Toast.makeText(CalificarOfertaActivity.this, "Oops se ha presentado un error leyendo los Criterios de Evaluación. ", Toast.LENGTH_LONG).show();
				progDailog.dismiss();
			}
		}
	}
	
}
