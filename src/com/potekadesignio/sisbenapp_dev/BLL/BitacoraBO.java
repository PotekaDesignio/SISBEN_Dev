package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class BitacoraBO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7144877726019768503L;
	private int intNumeroBitacora;
	private String strTituloBitacora;
	private String strTextoAnotacion;
	private Calendar calCalendario;
	private Date datFechaCreacion;
	
	public BitacoraBO()
	{
		strTituloBitacora = "";
		strTextoAnotacion = "";
		
		calCalendario = Calendar.getInstance();
		datFechaCreacion = (Date) calCalendario.getTime();
	}
	
	public int getIntNumeroBitacora() {
		return intNumeroBitacora;
	}
	public void setIntNumeroBitacora(int intNumeroBitacora) {
		this.intNumeroBitacora = intNumeroBitacora;
	}
	
	public String getStrTituloBitacora() {
		return strTituloBitacora;
	}
	public void setStrTituloBitacora(String strTituloBitacora) {
		this.strTituloBitacora = strTituloBitacora;
	}
	
	public String getStrTextoAnotacion() {
		return strTextoAnotacion;
	}
	public void setStrTextoAnotacion(String strTextoAnotacion) {
		this.strTextoAnotacion = strTextoAnotacion;
	}
	
	public Date getDatFechaCreacion() {
		return datFechaCreacion;
	}
	
}
