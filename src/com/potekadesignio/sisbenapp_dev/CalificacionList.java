package com.potekadesignio.sisbenapp_dev;

import java.util.ArrayList;

import com.poteka.ofertapp_dev.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalificacionList extends ArrayAdapter<String>
{

	private final Activity context;
	private final ArrayList<String> strTitulo;
	private final ArrayList<String> strSubTitulo;
	
	public CalificacionList(Activity context, ArrayList<String> strTitulo, ArrayList<String> strSubTitulo) 
	{
		super(context, R.layout.list_single, strTitulo);
		this.context = context;
		this.strTitulo = strTitulo;
		this.strSubTitulo = strSubTitulo;
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_calificacion, null, true);

		TextView txtTitulo = (TextView) rowView.findViewById(R.id.txtTitulo);
		TextView txtSubTitulo = (TextView) rowView.findViewById(R.id.txtSubTitulo);
		
		String strTituloArray[] = strTitulo.toArray(new String[strTitulo.size()]);
		String strSubTituloArray[] = strSubTitulo.toArray(new String[strSubTitulo.size()]);
		
		txtTitulo.setText(strTituloArray[position]);
		txtSubTitulo.setText(strSubTituloArray[position]);
				
		return rowView;
	}
	
	
}
