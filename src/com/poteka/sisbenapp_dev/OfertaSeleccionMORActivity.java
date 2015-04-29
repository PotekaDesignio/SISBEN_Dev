package com.poteka.sisbenapp_dev;

import java.io.File;
import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.OfertaSeleccionActivity;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OfertaSeleccionMORActivity extends Activity 
{
	private RelativeLayout rlyParaQuienesContenidoDesplegable;
	private RelativeLayout rlyQueEsContenidoDesplegable;
	private RelativeLayout rlyTipsContenidoDesplegable;
	private RelativeLayout rlyDondeContenidoDesplegable;
	private RelativeLayout rlyRequisitosContenidoDesplegable;
	
	private ImageView imgFondoDetalleDimension;
	private ImageView imgFondoDimension;
	private ImageView imgDimension;
	private ImageView imgFondoEncabezadoDescripcion;
	private ImageView imgParaQuienes; 
	private ImageView imgQueEs;
	private ImageView imgTips;
	private ImageView imgDonde;
	private ImageView imgCuando;
	
	private TextView txtTitulo;
	private TextView txtSubTitulo;
	private TextView txtTextoSuperiorOferta;
	private TextView txtDescripcionOferta;
	private TextView txtLogrosOferta;
	private TextView txtParaQuienesInfo;
	private TextView txtQueEsInfo;
	private TextView txtTipsInfo;
	private TextView txtDondeInfo;
	private TextView txtRequisitosInfo;
	
	
	private OfertaSimpleBO osOfertasMOR[];
	private OfertaSimpleBO osOfertaSeleccion;
	private String strOfertaDetalleID;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_oferta_seleccion_mor);
		
		Initlayout();
		InitVariables();
		CargarDatosOferta();
	}
	
	private void Initlayout() 
	{
		
		imgFondoDetalleDimension = (ImageView) findViewById(R.id.imgFondoDetalleDimension);
		imgFondoDimension = (ImageView) findViewById(R.id.imgFondoDimension);
		imgDimension = (ImageView) findViewById(R.id.imgDimension);
		imgFondoEncabezadoDescripcion = (ImageView) findViewById(R.id.imgFondoEncabezadoDescripcion);
		imgParaQuienes = (ImageView) findViewById(R.id.imgParaQuienes);
		imgQueEs = (ImageView) findViewById(R.id.imgQueEs);
		imgTips = (ImageView) findViewById(R.id.imgTips);
		imgDonde = (ImageView) findViewById(R.id.imgDonde);
		imgCuando = (ImageView) findViewById(R.id.imgCuando);
		
		txtTitulo = (TextView) findViewById(R.id.txtTitulo);
		txtSubTitulo = (TextView) findViewById(R.id.txtSubTitulo); 
		txtTextoSuperiorOferta = (TextView) findViewById(R.id.txtTextoSuperiorOferta);
		txtDescripcionOferta = (TextView) findViewById(R.id.txtDescripcionOferta);
		txtLogrosOferta = (TextView) findViewById(R.id.txtLogrosOferta);
		txtParaQuienesInfo = (TextView) findViewById(R.id.txtParaQuienesInfo);
		txtQueEsInfo = (TextView) findViewById(R.id.txtQueEsInfo);
		txtTipsInfo = (TextView) findViewById(R.id.txtTipsInfo);
		txtDondeInfo = (TextView) findViewById(R.id.txtDondeInfo);
		txtRequisitosInfo = (TextView) findViewById(R.id.txtRequisitosInfo);
		
		rlyParaQuienesContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyParaQuienesContenidoDesplegable);
		rlyQueEsContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyQueEsContenidoDesplegable);
		rlyTipsContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyTipsContenidoDesplegable);
		rlyDondeContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyDondeContenidoDesplegable);
		rlyRequisitosContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyRequisitosContenidoDesplegable);
		
		
		
		rlyParaQuienesContenidoDesplegable.setVisibility(View.GONE);
		rlyQueEsContenidoDesplegable.setVisibility(View.GONE);
		rlyTipsContenidoDesplegable.setVisibility(View.GONE);
		rlyDondeContenidoDesplegable.setVisibility(View.GONE);
		rlyRequisitosContenidoDesplegable.setVisibility(View.GONE);

	}
	
	private void InitVariables() 
	{
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) 
		{
			osOfertasMOR = (OfertaSimpleBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasMOROfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_MOR)));
			
			strOfertaDetalleID = extras.getString("DETALLE_OFERTA_ID");
			
			for(int i = 0; i < osOfertasMOR.length; i++)
	        {
				if(strOfertaDetalleID.equals(osOfertasMOR[i].getNumero()))
				{
					osOfertaSeleccion = osOfertasMOR[i];
					break;
				}
	        }
		}
		
	}
	
	private void CargarDatosOferta() 
	{
		switch(osOfertaSeleccion.getDimension())
		{
			case "identificacion":
				txtTitulo.setText("IDENTIFICACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_identificacion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_identificacion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_identificacion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_identificacion);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
								
				break;
			case "ingresos y trabajo":
				txtTitulo.setText("TRABAJO");
				
				imgDimension.setBackgroundResource(R.drawable.logo_trabajo);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_trabajo);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_trabajo);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_trabajo);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			
			case "educacion":
				txtTitulo.setText("EDUCACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_educacion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_educacion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_educacion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_educacion);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "salud":
				txtTitulo.setText("SALUD");
				
				imgDimension.setBackgroundResource(R.drawable.logo_salud);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_salud);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_salud);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_salud);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "nutricion":
				txtTitulo.setText("NUTRICION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_nutricion);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_nutricion);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_nutricion);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_nutricion);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "habitabilidad":
				txtTitulo.setText("HABITABILIDAD");
				
				imgDimension.setBackgroundResource(R.drawable.logo_habitabilidad);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_habitabilidad);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_habitabilidad);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_habitabilidad);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);				
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
								
				break;
			case "dinamica familiar":
				txtTitulo.setText("DINÁMICA FAMILIAR");
				
				imgDimension.setBackgroundResource(R.drawable.logo_familia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_familia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_familia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_familia);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "bancarizacion":
				txtTitulo.setText("BANCARIZACION");
				
				imgDimension.setBackgroundResource(R.drawable.logo_banca);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_banca);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_banca);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_banca);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "justicia":
				txtTitulo.setText("JUSTICIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_justicia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_justicia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_justicia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_justicia);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "primera infancia":
				txtTitulo.setText("PRIMERA INFANCIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_infancia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_infancia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_infancia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_infancia);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_infancia);
				imgQueEs.setBackgroundResource(R.drawable.fondo_infancia);
				imgTips.setBackgroundResource(R.drawable.fondo_infancia);
				imgDonde.setBackgroundResource(R.drawable.fondo_infancia);
				imgCuando.setBackgroundResource(R.drawable.fondo_infancia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case "comunitario":
				txtTitulo.setText("COMUNITARIO");
				
				imgDimension.setBackgroundResource(R.drawable.logo_comunitario);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_comunitario);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_comunitario);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_comunitario);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_comunitario);
				imgQueEs.setBackgroundResource(R.drawable.fondo_comunitario);
				imgTips.setBackgroundResource(R.drawable.fondo_comunitario);
				imgDonde.setBackgroundResource(R.drawable.fondo_comunitario);
				imgCuando.setBackgroundResource(R.drawable.fondo_comunitario);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			default:
				break;
		}
		
		txtSubTitulo.setText("Oferta Vigente");
		txtTextoSuperiorOferta.setText(osOfertaSeleccion.getEntidad().toUpperCase());
		txtDescripcionOferta.setText(osOfertaSeleccion.getOferta().toUpperCase());
		txtLogrosOferta.setText("Logro(s): " + osOfertaSeleccion.getLogro());
		txtParaQuienesInfo.setText(osOfertaSeleccion.getParaQuien().substring(0,1).toUpperCase() + osOfertaSeleccion.getParaQuien().substring(1).toLowerCase());
		txtQueEsInfo.setText(txtDescripcionOferta.getText() + ".\n" + osOfertaSeleccion.getQueEs().substring(0,1).toUpperCase() + osOfertaSeleccion.getQueEs().substring(1).toLowerCase());
		txtTipsInfo.setText(osOfertaSeleccion.getTip().substring(0,1).toUpperCase() + osOfertaSeleccion.getTip().substring(1).toLowerCase());
		txtDondeInfo.setText(osOfertaSeleccion.getDonde().substring(0,1).toUpperCase() + osOfertaSeleccion.getDonde().substring(1).toLowerCase());
		txtRequisitosInfo.setText(osOfertaSeleccion.getRequisitos().substring(0,1).toUpperCase() + osOfertaSeleccion.getRequisitos().substring(1).toLowerCase());
		
	}
	
	public void MostrarOcultar(View v)
	{
		String name = v.getTag().toString();
		int resID = getResources().getIdentifier(name + "ContenidoDesplegable", "id", getPackageName());
		final RelativeLayout test = (RelativeLayout) findViewById(resID);
		
		if(test.isShown())
		{
			PK_Utils.slide_up(OfertaSeleccionMORActivity.this, test);
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
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	
}
