package com.potekadesignio.sisbenapp_dev.BLL;

public class MunicipioBO {
	
	private int mun_MunicipioId;
	private String mun_CodigoMunicipioDANE;
	private String mun_NombreMunicipio;
	
	public MunicipioBO()
	{
		
	}
	
	public int getMunicipioId()
	{
		return mun_MunicipioId;
	}
	
	public void setMunicipioId(int pMuniId)
	{
		mun_MunicipioId = pMuniId;
	}
	
	public String getCodigoMuniDane()
	{
		return mun_CodigoMunicipioDANE;
	}
	public void setCodigoMuniDane(String pCodigoMuniDane)
	{
		mun_CodigoMunicipioDANE = pCodigoMuniDane;
	}
	
	public String getNombreMuni()
	{
		return mun_NombreMunicipio;
	}
	
	public void setNombreMuni(String pNombreMuni)
	{
		mun_NombreMunicipio = pNombreMuni;
	}
	

}
