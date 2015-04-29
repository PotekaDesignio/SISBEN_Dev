package com.potekadesignio.sisbenapp_dev;

import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>
{

	private final Activity context;
	private final ArrayList<String> strTitulo;
	private final ArrayList<String> strSubTitulo;
	private final ArrayList<Integer> intNumOfertas;
	private final ArrayList<Integer> imageId;
	private final ArrayList<Integer> imageBoton;
	
	
	public CustomList(Activity context, ArrayList<String> strTitulo2, ArrayList<String> strSubTitulo2, ArrayList<Integer> intImageId, ArrayList<Integer> intBotonFondoId, ArrayList<Integer> intNumOfertas2) 
	{
		super(context, R.layout.list_single, strTitulo2);
		this.context = context;
		this.strTitulo = strTitulo2;
		this.strSubTitulo = strSubTitulo2;
		this.imageId = intImageId;
		this.imageBoton = intBotonFondoId;
		this.intNumOfertas = intNumOfertas2;
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);

		TextView txtTitulo = (TextView) rowView.findViewById(R.id.txtTitulo);
		TextView txtSubTitulo = (TextView) rowView.findViewById(R.id.txtSubTitulo);
		TextView txtNumOfertas = (TextView) rowView.findViewById(R.id.txtNumOfertas);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imgDimension);
		ImageView row = (ImageView)rowView.findViewById(R.id.imgFondoDimension);

		RelativeLayout rlySeccionNumOfertas = (RelativeLayout)rowView.findViewById(R.id.rlyNumOfertas);
		
		String strTituloArray[] = strTitulo.toArray(new String[strTitulo.size()]);
		String strSubTituloArray[] = strSubTitulo.toArray(new String[strSubTitulo.size()]);
		
		Integer intNumOfertasArray[] = intNumOfertas.toArray(new Integer[intNumOfertas.size()]);
		Integer imageIdArray[] = imageId.toArray(new Integer[imageId.size()]);
		Integer imageBotonArray[] = imageBoton.toArray(new Integer[imageBoton.size()]);
		
		if(intNumOfertasArray[position] == 0)
		{
			rlySeccionNumOfertas.setVisibility(View.INVISIBLE);
		}
		else if(intNumOfertasArray[position] <= 9)
		{
			txtNumOfertas.setText(intNumOfertasArray[position].toString());
		}
		else
		{
			txtNumOfertas.setText("+9");
		}
		
		txtTitulo.setText(strTituloArray[position]);
		txtSubTitulo.setText(strSubTituloArray[position]);
		
		imageView.setImageResource(imageIdArray[position]);
		row.setImageResource(imageBotonArray[position]);
		
		return rowView;
	}
	
	
}
