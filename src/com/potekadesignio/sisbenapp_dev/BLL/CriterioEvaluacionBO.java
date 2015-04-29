package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;

public class CriterioEvaluacionBO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -608058260499255827L;
	private int cie_CirterioEvaluacionId;
	private String cie_Descripcion;
	private String cie_TextoGuia;
	private Boolean cia_Activo;
	
	public int getCie_CirterioEvaluacionId() {
		return cie_CirterioEvaluacionId;
	}
	public void setCie_CirterioEvaluacionId(int cie_CirterioEvaluacionId) {
		this.cie_CirterioEvaluacionId = cie_CirterioEvaluacionId;
	}
	
	public String getCie_Descripcion() {
		return cie_Descripcion;
	}
	public void setCie_Descripcion(String cie_Descripcion) {
		this.cie_Descripcion = cie_Descripcion;
	}
	
	public String getCie_TextoGuia() {
		return cie_TextoGuia;
	}
	public void setCie_TextoGuia(String cie_TextoGuia) {
		this.cie_TextoGuia = cie_TextoGuia;
	}
	
	public Boolean getCia_Activo() {
		return cia_Activo;
	}
	public void setCia_Activo(Boolean cia_Activo) {
		this.cia_Activo = cia_Activo;
	}

}
