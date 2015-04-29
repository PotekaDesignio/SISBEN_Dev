package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;


public class OfertaCompletaBO implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8008380;
	private int dim_DimensionId;
	private String dim_NombreDimension;
	private int ofe_OfertaId;
	private int ofd_DetalleOfertaId;
	private String ora_Descripcion;
	private String tpo_Descripcion;
	private String ofe_NombreOferente;
	private String ofe_NombreOferta;
	private String ofe_NombreOfertaCorto;
	private int log_LogroId;
	private int ofd_Cupos;
	private String ben_Descripcion;
	private String ofe_DescripcionOferta;
	private String ofe_TipOferta;
	private String ofd_LugarOferta;
	private String dof_FechaIniVigencia;
	private String dof_FechaFinVigencia;
	private String ofd_HoraInicio;
	private String ofd_HoraFin;
	private String ofe_RequisitosOferta;
	private String dof_MasInformacion;
	private Boolean bol_Agendada;
	
	public OfertaCompletaBO()
	{
		
	}
	
	public int getDim_DimensionId() 
	{
		return dim_DimensionId;
	}
	public void setDim_DimensionId(int dim_DimensionId) 
	{
		this.dim_DimensionId = dim_DimensionId;
	}

	public String getDim_NombreDimension() {
		return dim_NombreDimension;
	}
	public void setDim_NombreDimension(String dim_NombreDimension) {
		this.dim_NombreDimension = dim_NombreDimension;
	}
	
	public int getOfe_OfertaId() 
	{
		return ofe_OfertaId;
	}
	public void setOfe_OfertaId(int ofe_OfertaId) 
	{
		this.ofe_OfertaId = ofe_OfertaId;
	}
	
	public int getOfd_DetalleOfertaId() 
	{
		return ofd_DetalleOfertaId;
	}
	public void setOfd_DetalleOfertaId(int ofd_DetalleOfertaId) 
	{
		this.ofd_DetalleOfertaId = ofd_DetalleOfertaId;
	
	}
	
	public String getOra_Descripcion() 
	{
		return ora_Descripcion;
	}
	public void setOra_Descripcion(String ora_Descripcion) 
	{
		this.ora_Descripcion = ora_Descripcion;
	}
	
	public String getTpo_Descripcion() 
	{
		return tpo_Descripcion;
	}
	public void setTpo_Descripcion(String tpo_Descripcion) 
	{
		this.tpo_Descripcion = tpo_Descripcion;
	}
	
	public String getOfe_NombreOferente() 
	{
		return ofe_NombreOferente;
	}
	public void setOfe_NombreOferente(String ofe_NombreOferente) 
	{
		this.ofe_NombreOferente = ofe_NombreOferente;
	}
	
	public String getOfe_NombreOferta() 
	{
		return ofe_NombreOferta;
	}
	public void setOfe_NombreOferta(String ofe_NombreOferta) 
	{
		this.ofe_NombreOferta = ofe_NombreOferta;
	}
	
	public String getOfe_NombreOfertaCorto() 
	{
		return ofe_NombreOfertaCorto;
	}
	public void setOfe_NombreOfertaCorto(String ofe_NombreOfertaCorto) 
	{
		this.ofe_NombreOfertaCorto = ofe_NombreOfertaCorto;
	}
	
	public int getLog_LogroId() {
		return log_LogroId;
	}
	public void setLog_LogroId(int log_LogroId) {
		this.log_LogroId = log_LogroId;
	}
	
	public int getOfd_Cupos() {
		return ofd_Cupos;
	}
	public void setOfd_Cupos(int ofd_Cupos) {
		this.ofd_Cupos = ofd_Cupos;
	}
	
	public String getBen_Descripcion() {
		return ben_Descripcion;
	}
	public void setBen_Descripcion(String ben_Descripcion) {
		this.ben_Descripcion = ben_Descripcion;
	}

	public String getOfe_DescripcionOferta() {
		return ofe_DescripcionOferta;
	}
	public void setOfe_DescripcionOferta(String ofe_DescripcionOferta) {
		this.ofe_DescripcionOferta = ofe_DescripcionOferta;
	}

	public String getOfe_TipOferta() {
		return ofe_TipOferta;
	}
	public void setOfe_TipOferta(String ofe_TipOferta) {
		this.ofe_TipOferta = ofe_TipOferta;
	}
	
	public String getOfd_LugarOferta() {
		return ofd_LugarOferta;
	}
	public void setOfd_LugarOferta(String ofd_LugarOferta) {
		this.ofd_LugarOferta = ofd_LugarOferta;
	}
	
	public String getDof_FechaIniVigencia() {
		return dof_FechaIniVigencia;
	}
	public void setDof_FechaIniVigencia(String dof_FechaIniVigencia) {
		this.dof_FechaIniVigencia = dof_FechaIniVigencia;
	}
	
	public String getDof_FechaFinVigencia() {
		return dof_FechaFinVigencia;
	}
	public void setDof_FechaFinVigencia(String dof_FechaFinVigencia) {
		this.dof_FechaFinVigencia = dof_FechaFinVigencia;
	}
	
	public String getOfd_HoraInicio() {
		return ofd_HoraInicio;
	}
	public void setOfd_HoraInicio(String ofd_HoraInicio) {
		this.ofd_HoraInicio = ofd_HoraInicio;
	}
	
	public String getOfd_HoraFin() {
		return ofd_HoraFin;
	}
	public void setOfd_HoraFin(String ofd_HoraFin) {
		this.ofd_HoraFin = ofd_HoraFin;
	}
	
	public String getDof_MasInformacion() {
		return dof_MasInformacion;
	}
	public void setDof_MasInformacion(String dof_MasInformacion) {
		this.dof_MasInformacion = dof_MasInformacion;
	}
	
	public String getOfe_RequisitosOferta() {
		return ofe_RequisitosOferta;
	}
	public void setOfe_RequisitosOferta(String ofe_RequisitosOferta) {
		this.ofe_RequisitosOferta = ofe_RequisitosOferta;
	}
	public Boolean getBol_Agendada() {
		return bol_Agendada;
	}
	public void setBol_Agendada(Boolean bol_Agendada) {
		this.bol_Agendada = bol_Agendada;
	}
	
}
