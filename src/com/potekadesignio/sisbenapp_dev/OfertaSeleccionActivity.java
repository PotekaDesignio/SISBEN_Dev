package com.potekadesignio.sisbenapp_dev;


import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.poteka.ofertapp_dev.R;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.utils.PK_Utils;
import com.potekadesignio.sisbenapp_dev.utils.SaveObject_Helper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OfertaSeleccionActivity extends Activity 
{
	
	private RelativeLayout rlyParaQuienesContenidoDesplegable;
	private RelativeLayout rlyQueEsContenidoDesplegable;
	private RelativeLayout rlyTipsContenidoDesplegable;
	private RelativeLayout rlyDondeContenidoDesplegable;
	private RelativeLayout rlyCuandoContenidoDesplegable;
	private RelativeLayout rlyRequisitosContenidoDesplegable;
	private RelativeLayout rlyMasInfoContenidoDesplegable;
	
	private ImageView imgFondoDetalleDimension;
	private ImageView imgFondoDimension;
	private ImageView imgDimension;
	private ImageView imgFondoEncabezadoDescripcion;
	private ImageView imgParaQuienes; 
	private ImageView imgQueEs;
	private ImageView imgTips;
	private ImageView imgDonde;
	private ImageView imgCuando;
	private ImageView imgRequisitos;
	private ImageView imgMasInfo;
	private ImageView imgAgendarmeInfo;
	
	private TextView txtTitulo;
	private TextView txtSubTitulo;
	private TextView txtTextoSuperiorOferta;
	private TextView txtDescripcionOferta;
	private TextView txtLogrosOferta;
	private TextView txtParaQuienesInfo;
	private TextView txtQueEsInfo;
	private TextView txtTipsInfo;
	private TextView txtDondeInfo;
	private TextView txtCuandoInfo;
	private TextView txtRequisitosInfo;
	private TextView txtMasInfoInfo;
	
	private OfertaCompletaBO[] osOfertasUsuario;
	private ArrayList<OfertaCompletaBO> osOfertasAgendadas;
	private OfertaCompletaBO osOfertaSeleccion;
	private String intOfertaDetalleID;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_oferta_seleccion);
		
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
		imgRequisitos = (ImageView) findViewById(R.id.imgRequisitos);
		imgMasInfo = (ImageView) findViewById(R.id.imgMasInfo);
		imgAgendarmeInfo = (ImageView) findViewById(R.id.imgAgendarmeInfo);
		
		
		txtTitulo = (TextView) findViewById(R.id.txtTitulo);
		txtSubTitulo = (TextView) findViewById(R.id.txtSubTitulo); 
		txtTextoSuperiorOferta = (TextView) findViewById(R.id.txtTextoSuperiorOferta);
		txtDescripcionOferta = (TextView) findViewById(R.id.txtDescripcionOferta);
		txtLogrosOferta = (TextView) findViewById(R.id.txtLogrosOferta);
		txtParaQuienesInfo = (TextView) findViewById(R.id.txtParaQuienesInfo);
		txtQueEsInfo = (TextView) findViewById(R.id.txtQueEsInfo);
		txtTipsInfo = (TextView) findViewById(R.id.txtTipsInfo);
		txtDondeInfo = (TextView) findViewById(R.id.txtDondeInfo);
		txtCuandoInfo = (TextView) findViewById(R.id.txtCuandoInfo);
		txtRequisitosInfo = (TextView) findViewById(R.id.txtRequisitosInfo);
		txtMasInfoInfo = (TextView) findViewById(R.id.txtMasInfoInfo);
		
		
		rlyParaQuienesContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyParaQuienesContenidoDesplegable);
		rlyQueEsContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyQueEsContenidoDesplegable);
		rlyTipsContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyTipsContenidoDesplegable);
		rlyDondeContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyDondeContenidoDesplegable);
		rlyCuandoContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyCuandoContenidoDesplegable);
		rlyRequisitosContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyRequisitosContenidoDesplegable);
		rlyMasInfoContenidoDesplegable = (RelativeLayout) findViewById(R.id.rlyMasInfoContenidoDesplegable);
		
		
		rlyParaQuienesContenidoDesplegable.setVisibility(View.GONE);
		rlyQueEsContenidoDesplegable.setVisibility(View.GONE);
		rlyTipsContenidoDesplegable.setVisibility(View.GONE);
		rlyDondeContenidoDesplegable.setVisibility(View.GONE);
		rlyCuandoContenidoDesplegable.setVisibility(View.GONE);
		rlyRequisitosContenidoDesplegable.setVisibility(View.GONE);
		rlyMasInfoContenidoDesplegable.setVisibility(View.GONE);
	}
	
	private void InitVariables() 
	{
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) 
		{
			osOfertasUsuario = (OfertaCompletaBO[])new SaveObject_Helper().CargarInfoSerializadaOfertasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS)));
			osOfertasAgendadas = (ArrayList<OfertaCompletaBO>)new SaveObject_Helper().CargarInfoSerializadaOfertasAgendadasBO(new File(getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS)));
			
			intOfertaDetalleID = extras.getString("DETALLE_OFERTA_ID");
			
			for(int i = 0; i < osOfertasUsuario.length; i++)
	        {
				if(Integer.parseInt(intOfertaDetalleID) == osOfertasUsuario[i].getOfd_DetalleOfertaId())
				{
					osOfertaSeleccion = osOfertasUsuario[i];
					break;
				}
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
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_identificacion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
								
				break;
			case 2:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_trabajo);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			
			case 3:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_educacion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 4:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_salud);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 5:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_nutricion);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 6:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_habitabilidad);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
								
				break;
			case 7:
				txtTitulo.setText("FAMILIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_familia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_familia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_detalle_familia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_encabezado_descripcion_familia);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgQueEs.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgTips.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgDonde.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgCuando.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_familia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));				
				
				break;
			case 8:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_banca);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 9:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_descripcion_seccion_justicia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 10:
				txtTitulo.setText("INFANCIA");
				
				imgDimension.setBackgroundResource(R.drawable.logo_infancia);
				imgFondoDimension.setBackgroundResource(R.drawable.fondo_infancia);
				imgFondoDetalleDimension.setBackgroundResource(R.drawable.fondo_infancia);
				
				imgFondoEncabezadoDescripcion.setBackgroundResource(R.drawable.fondo_infancia);
				imgParaQuienes.setBackgroundResource(R.drawable.fondo_infancia);
				imgQueEs.setBackgroundResource(R.drawable.fondo_infancia);
				imgTips.setBackgroundResource(R.drawable.fondo_infancia);
				imgDonde.setBackgroundResource(R.drawable.fondo_infancia);
				imgCuando.setBackgroundResource(R.drawable.fondo_infancia);
				imgRequisitos.setBackgroundResource(R.drawable.fondo_infancia);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_infancia);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_infancia);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			case 11:
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
				imgRequisitos.setBackgroundResource(R.drawable.fondo_comunitario);
				imgMasInfo.setBackgroundResource(R.drawable.fondo_comunitario);
				imgAgendarmeInfo.setBackgroundResource(R.drawable.fondo_comunitario);
				
				rlyParaQuienesContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100")); 
				rlyQueEsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyTipsContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyDondeContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyCuandoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyRequisitosContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				rlyMasInfoContenidoDesplegable.setBackgroundColor(Color.parseColor("#FFD100"));
				
				break;
			default:
				break;
		}
		
		txtSubTitulo.setText(osOfertaSeleccion.getOfe_NombreOferta());
		txtTextoSuperiorOferta.setText(osOfertaSeleccion.getOra_Descripcion() + " - " + osOfertaSeleccion.getOfe_NombreOferente() + " - " + osOfertaSeleccion.getTpo_Descripcion());
		txtDescripcionOferta.setText(osOfertaSeleccion.getOfe_NombreOfertaCorto().toUpperCase());
		txtLogrosOferta.setText("Logro(s): " + osOfertaSeleccion.getLog_LogroId() + "- Cupo(s): " +osOfertaSeleccion.getOfd_Cupos());
		txtParaQuienesInfo.setText(osOfertaSeleccion.getBen_Descripcion());
		txtQueEsInfo.setText(osOfertaSeleccion.getOfe_NombreOferta() + "\n" +osOfertaSeleccion.getOfe_DescripcionOferta());
		txtTipsInfo.setText(osOfertaSeleccion.getOfe_TipOferta());
		txtDondeInfo.setText(osOfertaSeleccion.getOfd_LugarOferta());
		txtCuandoInfo.setText(osOfertaSeleccion.getDof_FechaIniVigencia() + " - " + osOfertaSeleccion.getDof_FechaFinVigencia() + " \nHora Inicio: " + osOfertaSeleccion.getOfd_HoraInicio() + "\nHora Fin: " + osOfertaSeleccion.getOfd_HoraFin());
		txtRequisitosInfo.setText(osOfertaSeleccion.getOfe_RequisitosOferta());
		txtMasInfoInfo.setText(osOfertaSeleccion.getDof_MasInformacion());
		
	}
	
	public void MostrarOcultar(View v)
	{
		String name = v.getTag().toString();
		int resID = getResources().getIdentifier(name + "ContenidoDesplegable", "id", getPackageName());
		final RelativeLayout test = (RelativeLayout) findViewById(resID);
		
		if(test.isShown())
		{
			PK_Utils.slide_up(OfertaSeleccionActivity.this, test);
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
	
	public void btnAgendarme_click(View v)
	{
		/*
		int intEvenntoId = new PK_Utils().ListSelectedCalendars(getApplicationContext(), osOfertaSeleccion.getOfe_NombreOferta());
		
		if(intEvenntoId == 0)
		{
			Intent calIntent = new Intent(Intent.ACTION_EDIT); 
			calIntent.setType("vnd.android.cursor.item/event");    
			calIntent.putExtra(Events.TITLE, osOfertaSeleccion.getOfe_NombreOferta()); 
			calIntent.putExtra(Events.EVENT_LOCATION, osOfertaSeleccion.getOfd_LugarOferta()); 
			calIntent.putExtra(Events.DESCRIPTION, osOfertaSeleccion.getOfe_DescripcionOferta()); 
			 
			String segments[] = osOfertaSeleccion.getDof_FechaIniVigencia().split("/");
			
			//GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
			GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(segments[2]), Integer.parseInt(segments[1]) - 1, Integer.parseInt(segments[0]));
			
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
			     calDate.getTimeInMillis()); 
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
			     calDate.getTimeInMillis()); 
			 
			startActivityForResult(calIntent, 10);
		}
		else
		{
			Toast.makeText(OfertaSeleccionActivity.this, "¡Así es, ya te has agendado previamente para ésta Oferta!", Toast.LENGTH_LONG).show();
		}
		*/
		
		boolean bolExiste = false;
		
		try
		{
			
			for(OfertaCompletaBO oferta : osOfertasAgendadas)
			{
				if(oferta.getOfe_OfertaId() == osOfertaSeleccion.getOfe_OfertaId())
				{
					bolExiste = true;
					break;
				}
				
			}
			
			if(!bolExiste)
			{
				osOfertasAgendadas.add(osOfertaSeleccion);
				new SaveObject_Helper().GuardarInfoOfertasAgendadasBO(osOfertasAgendadas, getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS));	
				Toast.makeText(OfertaSeleccionActivity.this, "¡Excelente! Ya estás agendado para ésta Oferta. No olvides calificarla", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(OfertaSeleccionActivity.this, "¡Así es, ya te has agendado previamente para ésta Oferta!", Toast.LENGTH_LONG).show();
			}
			
		}
		catch(Exception ex)
		{
			Toast.makeText(OfertaSeleccionActivity.this, "¡Oops! Se ha presentado un error al agendarte...", Toast.LENGTH_LONG).show();
		
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		/*
		new PK_Utils().SincronizarCalendario(getApplicationContext());
		int intEvenntoId = new PK_Utils().ListSelectedCalendars(getApplicationContext(), osOfertaSeleccion.getOfe_NombreOferta());
		
		if(intEvenntoId != 0)
		{
			Toast.makeText(OfertaSeleccionActivity.this, "¡Excelente! Ya estás agendado para ésta Oferta. No olvides calificarla", Toast.LENGTH_LONG).show();
			osOfertasAgendadas.add(osOfertaSeleccion);
			new SaveObject_Helper().GuardarInfoOfertasAgendadasBO(osOfertasAgendadas, getString(R.string.RUTA_NOMBRE_DATOS_OFERTAS_AGENDADAS));
		}
		else
		{
			Toast.makeText(OfertaSeleccionActivity.this, "El evento no ha sido creado", Toast.LENGTH_LONG).show();
		}
		*/
	}
	
	@Override
	public void onBackPressed() 
	{
	    super.onBackPressed();
	    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
