package com.potekadesignio.sisbenapp_dev.BLL;

public class DepartamentoBO {
	
	private int dep_DepartamentoId;
	private String dep_CodigoDepartamentoDANE;
	private String dep_NombreDepartamento;
	
	public DepartamentoBO()
	{
		
	}
	
	public int getDepartamentoId()
	{
		return dep_DepartamentoId;
	}
	
	public void setDepartamentoId(int pDeptoId)
	{
		dep_DepartamentoId = pDeptoId;
	}
	
	public String getCodigoDeptoDane()
	{
		return dep_CodigoDepartamentoDANE;
	}
	public void setCodigoDeptoDane(String pCodigoDeptoDane)
	{
		dep_CodigoDepartamentoDANE = pCodigoDeptoDane;
	}
	
	public String getNombreDepto()
	{
		return dep_NombreDepartamento;
	}
	
	public void setNombreDepto(String pNombreDepto)
	{
		dep_NombreDepartamento = pNombreDepto;
	}
	

}
