package com.potekadesignio.sisbenapp_dev.BLL;

import java.io.Serializable;
import java.util.Date;

public class UsuarioBO implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8008135L;
	private int usu_UsuarioId;
	private String usu_NombresUsuario;
	private String usu_ApelidosUsuario;
	private String usu_CorreoElectronico;
	private String usu_Password;
	private String dep_CodigoDepartamentoDANE;
	private String mun_CodigoMunicipioDANE;
	private boolean usu_SesionIniciada;
	private boolean usu_Activo; 
	private String ins_CodigoInstancia;
	private int tii_TipoInsanciaId;
	
	private Date sis_FechaCreacion;
	private Date sis_FechaModificacion;
	
	public UsuarioBO()
	{
		
	}
	
	public int getUsuarioId()
	{
		return usu_UsuarioId;
	}
	public void setUsuarioId(int pUsuarioId)
	{
		usu_UsuarioId = pUsuarioId;
	}
	
	public String getNombresUsuario()
	{
		return usu_NombresUsuario;
	}
	public void setNombresUsuario(String pNombresUsuario)
	{
		usu_NombresUsuario = pNombresUsuario;
	}
	
	public String getApellidosUsuario()
	{
		return usu_ApelidosUsuario;
	}
	public void setApellidosUsuario(String pApellidosUsuario)
	{
		usu_ApelidosUsuario = pApellidosUsuario;
	}
	
	public String getCorreoElectronico()
	{
		return usu_CorreoElectronico;
	}
	public void setCorreoElectronico(String pCorreoElectronico)
	{
		usu_CorreoElectronico = pCorreoElectronico;
	}

	public String getPassword()
	{
		return usu_Password;
	}
	public void setPassword(String pPassword)
	{
		usu_Password = pPassword;
	}
	
	public String getCodigoDepartamento()
	{
		return dep_CodigoDepartamentoDANE;
	}
	public void setCodigoDepartamento(String pCodigoDepartamento)
	{
		dep_CodigoDepartamentoDANE = pCodigoDepartamento;
	}
	
	public String getCodigoMunicipio()
	{
		return mun_CodigoMunicipioDANE;
	}
	public void setCodigoMunicipio(String pCodigoMunicipio)
	{
		mun_CodigoMunicipioDANE = pCodigoMunicipio;
	}
	
	public boolean getSesioniniciada()
	{
		return usu_SesionIniciada;
	}
	public void setSesionIniciada(boolean pSesionIniciada)
	{
		usu_SesionIniciada = pSesionIniciada;
	}
	
	public boolean getActivo()
	{
		return usu_Activo;
	}
	public void setActivo(boolean pActivo)
	{
		usu_Activo = pActivo;
	}
	
	public String getInstanciaId()
	{
		return ins_CodigoInstancia;
	}
	public void setInstanciaId(String pInstanciaId)
	{
		ins_CodigoInstancia = pInstanciaId;
	}
		
	public int getTipoInstancia()
	{
		return tii_TipoInsanciaId;
	}
	public void setTipoInstancia(int pTipoInstancia)
	{
		tii_TipoInsanciaId = pTipoInstancia;
	}
	
	public Date getFechaCreacion()
	{
		return sis_FechaCreacion;
	}
	
	public Date getFechaModificacion()
	{
		return sis_FechaModificacion;
	}

}
