package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;

public class DimensionBO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7161877726019768503L;
	private int dim_DimensionId;
	private String dim_NumeroMOR;
	private String dim_NombreDimension;
	private Boolean dim_Activo;
	
	
	public int getDim_DimensionId() {
		return dim_DimensionId;
	}
	public void setDim_DimensionId(int dim_DimensionId) {
		this.dim_DimensionId = dim_DimensionId;
	}
	
	public String getDim_NumeroMOR() {
		return dim_NumeroMOR;
	}
	public void setDim_NumeroMOR(String dim_NumeroMOR) {
		this.dim_NumeroMOR = dim_NumeroMOR;
	}
	
	public String getDim_NombreDimension() {
		return dim_NombreDimension;
	}
	public void setDim_NombreDimension(String dim_NombreDimension) {
		this.dim_NombreDimension = dim_NombreDimension;
	}
	
	public Boolean getDim_Activo() {
		return dim_Activo;
	}
	public void setDim_Activo(Boolean dim_Activo) {
		this.dim_Activo = dim_Activo;
	}
}
