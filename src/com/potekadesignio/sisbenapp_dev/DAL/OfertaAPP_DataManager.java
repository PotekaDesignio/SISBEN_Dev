package com.potekadesignio.sisbenapp_dev.DAL;


import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.potekadesignio.sisbenapp_dev.BLL.CriterioEvaluacionBO;
import com.potekadesignio.sisbenapp_dev.BLL.DepartamentoBO;
import com.potekadesignio.sisbenapp_dev.BLL.DimensionBO;
import com.potekadesignio.sisbenapp_dev.BLL.MunicipioBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;

//import android.util.Base64;
//import android.util.Log;

public class OfertaAPP_DataManager
{
	public String SOAP_ACTION; //("http://tempuri.org/VerificarUsuarioXML");
	public String WSDL_TARGET_NAMESPACE; //("http://tempuri.org/");
	public String SOAP_ADDRESS; //("http://192.168.1.3:3146/wsNeuroSistemasMobile.asmx");
	
	
    public UsuarioBO LoginUsuario (SoapObject soap)
    {
    	UsuarioBO UsuarioLogged = new UsuarioBO();
    	
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0); 
    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
    	
    	soap.getPropertyInfo(0, pi);
    	sop.getPropertyInfo(0, pii);
		
    	
    	for (int i = 0; i < sop.getPropertyCount(); i++) 
        {
    		UsuarioLogged.setUsuarioId(Integer.parseInt(sop1.getProperty(0).toString())) ;
    		UsuarioLogged.setNombresUsuario(sop1.getProperty(1).toString());       	
    		UsuarioLogged.setApellidosUsuario(sop1.getProperty(2).toString());
    		UsuarioLogged.setCorreoElectronico(sop1.getProperty(3).toString());
    		UsuarioLogged.setPassword(sop1.getProperty(4).toString());
    		UsuarioLogged.setCodigoDepartamento(sop1.getProperty(5).toString());
    		UsuarioLogged.setCodigoMunicipio(sop1.getProperty(6).toString());
    		UsuarioLogged.setSesionIniciada(Boolean.valueOf(sop1.getProperty(7).toString()));
    		UsuarioLogged.setActivo(Boolean.valueOf(sop1.getProperty(8).toString()));
        }
        
        return UsuarioLogged;
    }
    
    public DepartamentoBO[] ObtenerDepartamentos(SoapObject soap)
    {
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0); 
    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
    	
    	soap.getPropertyInfo(0, pi);
    	sop.getPropertyInfo(0, pii);
    	
    	DepartamentoBO[] Departamentos = new DepartamentoBO[sop.getPropertyCount()];
    	
		for (int i = 0; i < sop.getPropertyCount(); i++) 
        {
			sop1 = (SoapObject) sop.getProperty(i);
			Departamentos[i] = new DepartamentoBO();
			Departamentos[i].setDepartamentoId(Integer.parseInt(sop1.getProperty(0).toString()));
			Departamentos[i].setCodigoDeptoDane(sop1.getProperty(1).toString());
			Departamentos[i].setNombreDepto(sop1.getProperty(2).toString());
    		
        }
    	
		return Departamentos; 	
    
    }
    
    public MunicipioBO[] ObtenerMunicipiosDepto(SoapObject soap)
    {
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0); 
    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
    	
    	soap.getPropertyInfo(0, pi);
    	sop.getPropertyInfo(0, pii);
    	
    	MunicipioBO[] Municipios = new MunicipioBO[sop.getPropertyCount()];
    	
		for (int i = 0; i < sop.getPropertyCount(); i++) 
        {
			sop1 = (SoapObject) sop.getProperty(i);
			Municipios[i] = new MunicipioBO();
			Municipios[i].setMunicipioId(Integer.parseInt(sop1.getProperty(0).toString()));
			Municipios[i].setCodigoMuniDane(sop1.getProperty(1).toString());
			Municipios[i].setNombreMuni(sop1.getProperty(3).toString());
    		
        }
	
		return Municipios; 	
    
    }
    
    public OfertaCompletaBO[] ObtenerOfertasUsuarioWS(SoapObject soap)
    {
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0);
	    	
    	if(sop.getPropertyCount() > 0)
    	{
    	
	    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
	    	
	    	soap.getPropertyInfo(0, pi);
	    	sop.getPropertyInfo(0, pii);
	    	
	    	OfertaCompletaBO[] OfertasUsuarioWS = new OfertaCompletaBO[sop.getPropertyCount()];
	    	
	    	for (int i = 0; i < sop.getPropertyCount(); i++) 
	        {
				sop1 = (SoapObject) sop.getProperty(i);
				OfertasUsuarioWS[i] = new OfertaCompletaBO();
				
				OfertasUsuarioWS[i].setDim_DimensionId(Integer.parseInt(sop1.getProperty("dim_DimensionId").toString()));
				OfertasUsuarioWS[i].setDim_NombreDimension(sop1.getProperty("dim_NombreDimension").toString());
				OfertasUsuarioWS[i].setOfe_OfertaId(Integer.parseInt(sop1.getProperty("ofe_OfertaId").toString()));
				OfertasUsuarioWS[i].setOfd_DetalleOfertaId(Integer.parseInt(sop1.getProperty("ofd_DetalleOfertaId").toString()));
				
				OfertasUsuarioWS[i].setOra_Descripcion(sop1.getProperty("ora_Descripcion").toString());
				OfertasUsuarioWS[i].setTpo_Descripcion(sop1.getProperty("tpo_Descripcion").toString());
				OfertasUsuarioWS[i].setOfe_NombreOferente(sop1.getProperty("ofe_NombreOferente").toString());
				OfertasUsuarioWS[i].setOfe_NombreOferta(sop1.getProperty("ofe_NombreOferta").toString());
				OfertasUsuarioWS[i].setOfe_NombreOfertaCorto(sop1.getProperty("ofe_NombreOfertaCorto").toString());
				
				OfertasUsuarioWS[i].setLog_LogroId(Integer.parseInt(sop1.getProperty("log_LogroId").toString()));
				OfertasUsuarioWS[i].setOfd_Cupos(Integer.parseInt(sop1.getProperty("ofd_Cupos").toString()));
				
				OfertasUsuarioWS[i].setBen_Descripcion(sop1.getProperty("ben_Descripcion").toString());
				OfertasUsuarioWS[i].setOfe_DescripcionOferta(sop1.getProperty("ofe_DescripcionOferta").toString());
				OfertasUsuarioWS[i].setOfe_TipOferta(sop1.getProperty("ofe_TipOferta").toString());
				OfertasUsuarioWS[i].setOfd_LugarOferta(sop1.getProperty("ofd_LugarOferta").toString());
				
				OfertasUsuarioWS[i].setDof_FechaIniVigencia(sop1.getProperty("dof_FechaIniVigencia").toString());
				OfertasUsuarioWS[i].setDof_FechaFinVigencia(sop1.getProperty("dof_FechaFinVigencia").toString());
				OfertasUsuarioWS[i].setOfd_HoraInicio(sop1.getProperty("ofd_HoraInicio").toString());
				OfertasUsuarioWS[i].setOfd_HoraFin(sop1.getProperty("ofd_HoraFin").toString());
				OfertasUsuarioWS[i].setOfe_RequisitosOferta(sop1.getProperty("ofe_RequisitosOferta").toString());
						
				OfertasUsuarioWS[i].setDof_MasInformacion(sop1.getProperty("dof_MasInformacion").toString());
	        }
	    	
	    	return OfertasUsuarioWS;
    	}
    	else
    		return null;
    
    }

    public DimensionBO[] ObtenerDimensiones(SoapObject soap)
    {
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0); 
    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
    	
    	soap.getPropertyInfo(0, pi);
    	sop.getPropertyInfo(0, pii);
    	
    	DimensionBO[] Dimensiones = new DimensionBO[sop.getPropertyCount()];
    	
		for (int i = 0; i < sop.getPropertyCount(); i++) 
        {
			sop1 = (SoapObject) sop.getProperty(i);
			Dimensiones[i] = new DimensionBO();
			Dimensiones[i].setDim_DimensionId(Integer.parseInt(sop1.getProperty("dim_DimensionId").toString()));
			//Dimensiones[i].setDim_NumeroMOR(sop1.getProperty("dim_NumeroMOR").toString());
			Dimensiones[i].setDim_NombreDimension(sop1.getProperty("dim_NombreDimension").toString());
        }
    	
		return Dimensiones; 	
    
    }
    
    public CriterioEvaluacionBO[] ObtenerCriteriosEvaluacion(SoapObject soap)
    {
    	PropertyInfo pi = new PropertyInfo();
    	PropertyInfo pii = new PropertyInfo();
    	    	
    	SoapObject sop = (SoapObject) soap.getProperty(0); 
    	SoapObject sop1 = (SoapObject) sop.getProperty(0);
    	
    	soap.getPropertyInfo(0, pi);
    	sop.getPropertyInfo(0, pii);
    	
    	CriterioEvaluacionBO[] Criterios = new CriterioEvaluacionBO[sop.getPropertyCount()];
    	
		for (int i = 0; i < sop.getPropertyCount(); i++) 
        {
			sop1 = (SoapObject) sop.getProperty(i);
			Criterios[i] = new CriterioEvaluacionBO();
			Criterios[i].setCie_CirterioEvaluacionId(Integer.parseInt(sop1.getProperty(0).toString()));
			Criterios[i].setCie_Descripcion(sop1.getProperty(1).toString());
			Criterios[i].setCie_TextoGuia(sop1.getProperty(2).toString());
			Criterios[i].setCia_Activo(Boolean.valueOf(sop1.getProperty(2).toString()));
    		
        }
    	
		return Criterios; 	
    }
    
}
