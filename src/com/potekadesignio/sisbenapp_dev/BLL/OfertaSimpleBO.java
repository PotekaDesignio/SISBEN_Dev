package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;

public class OfertaSimpleBO implements Serializable 
{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 80085;
	private String strDimension;
	private String strNumero;
	private String strEntidad;
	private String strOferta;
	private String strLogro;
	private String strQueEs;
	private String strParaQuien;
	private String strDonde;
	private String strRequisitos;
	private String strTip;
	
	public OfertaSimpleBO()
	{
		
	}
	
	public String getDimension()
	{
		return strDimension;
	}
	public void setDimension(String pDimension)
	{
		strDimension = pDimension;
	}
	
	public String getNumero()
	{
		return strNumero;
	}
	public void setNumero(String pNumero)
	{
		strNumero = pNumero;
	}
	
	public String getEntidad()
	{
		return strEntidad;
	}
	public void setEntidad(String pEntidad)
	{
		strEntidad = pEntidad;
	}
	
	public String getOferta()
	{
		return strOferta;
	}
	public void setOferta(String pOferta)
	{
		strOferta = pOferta;
	}
	
	public String getLogro()
	{
		return strLogro;
	}
	public void setLogro(String pLogro)
	{
		strLogro = pLogro;
	}
	
	public String getQueEs()
	{
		return strQueEs;
	}
	public void setQueEs(String pQueEs)
	{
		strQueEs = pQueEs;
	}
	
	public String getParaQuien()
	{
		return strParaQuien;
	}
	public void setParaQuien(String pParaQuien)
	{
		strParaQuien = pParaQuien;
	}
	
	public String getDonde()
	{
		return strDonde;
	}
	public void setDonde(String pDonde)
	{
		strDonde = pDonde;
	}

	public String getRequisitos()
	{
		return strRequisitos;
	}
	public void setRequisitos(String pRequisitos)
	{
		strRequisitos = pRequisitos;
	}
	
	public String getTip()
	{
		return strTip;
	}
	public void setTip(String pTip)
	{
		strTip = pTip;
	}
	
}
