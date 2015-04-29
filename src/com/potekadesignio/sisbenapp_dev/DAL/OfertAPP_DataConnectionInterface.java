package com.potekadesignio.sisbenapp_dev.DAL;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;


public class OfertAPP_DataConnectionInterface 
{
	
	private OfertAPP_DataConnectParameters wsServicio;
	
	public OfertAPP_DataConnectionInterface() 
	{ 
		wsServicio = new OfertAPP_DataConnectParameters();
		
		/*
		wsPrueba.setSOAP_ACTION("http://tempuri.org/Add");
		wsPrueba.setWSDL_TARGET_NAMESPACE("http://tempuri.org/");
		wsPrueba.setSOAP_ADDRESS("http://grasshoppernetwork.com/NewFile.asmx");
		
		wsPrueba.setSOAP_ACTION("http://tempuri.org/VerificarUsuarioXML");
		wsPrueba.setWSDL_TARGET_NAMESPACE("http://tempuri.org/");
		wsPrueba.setSOAP_ADDRESS("http://192.168.1.3:3146/wsNeuroSistemasMobile.asmx");
		
		*/
	}
	
	public OfertAPP_DataConnectionInterface(String WSDL_TARGET_NAMESPACE, String SOAP_ADDRESS)
	{
		wsServicio = new OfertAPP_DataConnectParameters();
		
		wsServicio.setWSDL_TARGET_NAMESPACE(WSDL_TARGET_NAMESPACE);
		wsServicio.setSOAP_ADDRESS(SOAP_ADDRESS);
	}
    
	
	public void wsDataManager_WSDL_TARGET_NAMESPACE(String strWSDL_TARGET_NAMESPACE)
	{
		wsServicio.setWSDL_TARGET_NAMESPACE(strWSDL_TARGET_NAMESPACE);
	}
	
	public void wsDataManager_SOAP_ADDRESS(String strSOAP_ADDRESS)
	{
		wsServicio.setSOAP_ADDRESS(strSOAP_ADDRESS);
	}
	
	public void wsDataManager_OperationName(String strMethodName)
	{
		wsServicio.setSOAP_ACTION(wsServicio.getWSDL_TARGET_NAMESPACE() + strMethodName);
		wsServicio.setOPERATION_NAME(strMethodName);
	}
	
	public SoapObject ExecWSMethod_Select(PropertyInfo piParameters[], String strMethodName)
	{
		try 
		{
			wsServicio.setOPERATION_NAME(strMethodName);
			SoapObject result = wsServicio.ConsumeWebService(piParameters);
			return result;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public SoapObject ExecWSMethod_Insert(PropertyInfo piParameters[], String strMethodName) throws Exception
	{
		try 
		{
			wsServicio.setOPERATION_NAME(strMethodName);
			return wsServicio.ConsumeWebService(piParameters);
		} 
		catch (Exception e) 
		{
			String error = e.getMessage();
			throw new Exception(error);
		}
	}
	
	public SoapObject ExecWSMethod_Update(PropertyInfo piParameters[], String strMethodName)
	{
		try 
		{
			wsServicio.setOPERATION_NAME(strMethodName);
			SoapObject result = wsServicio.ConsumeWebService(piParameters);
			return result;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}

