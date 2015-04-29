package com.potekadesignio.sisbenapp_dev.DAL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.potekadesignio.sisbenapp_dev.Marshals.MarshalDouble;

public class OfertAPP_DataConnectParameters 
{
	//Variables Privadas//
	private String SOAP_ACTION;
	private String OPERATION_NAME; 
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_ADDRESS;
	private SoapObject request;
	private SoapSerializationEnvelope envelope;
	private HttpTransportSE httpTransport;
	private SoapObject response;
	public enum Operacion { SELECT, INSERT, UPDATE, DELETE } 
	
	//Setters & Getters
	
	public void setSOAP_ACTION(String strMethodAction)
	{
		SOAP_ACTION  = strMethodAction;
	}
	public void setOPERATION_NAME(String strMethodName)
	{
		OPERATION_NAME = strMethodName;
	}
	public void setWSDL_TARGET_NAMESPACE(String strWSDLTargetNamespace)
	{
		WSDL_TARGET_NAMESPACE = strWSDLTargetNamespace;
	}
	public void setSOAP_ADDRESS(String strSoapAddress)
	{
		SOAP_ADDRESS = strSoapAddress;
	}
	
	public String getSOAP_ACTION()
	{
		return SOAP_ACTION;
	}
	public String getOPERATION_NAME()
	{
		return OPERATION_NAME;
	}
	public String getWSDL_TARGET_NAMESPACE()
	{
		return WSDL_TARGET_NAMESPACE;
	}
	public String getSOAP_ADDRESS()
	{
		return SOAP_ADDRESS;
	}
	
	public OfertAPP_DataConnectParameters()
	{
		SOAP_ACTION = "";
		OPERATION_NAME = ""; 
		WSDL_TARGET_NAMESPACE = "";
		SOAP_ADDRESS = "";
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		response=null;
	}
	
	public SoapObject ConsumeWebService(PropertyInfo piParameters[]) throws Exception
	{
		request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
		
		if(piParameters != null && piParameters.length > 0)
		{
			for(PropertyInfo piParameter:piParameters)
			{
				PropertyInfo pi = new PropertyInfo();
			    pi.setName(piParameter.getName()); //change to appropriate type e.g. String
			    pi.setValue(piParameter.getValue()); // if sString add the speech marks e.g. "5"
			    pi.setType(piParameter.getType());                  
			    request.addProperty(pi);
			}
		}
		
		MarshalDouble md = new MarshalDouble();
		MarshalBase64 m64 = new MarshalBase64();
		//m64.register(envelope);
		//md.register(envelope);
		
		//new MarshalBase64().register(envelope);
		
		envelope.setOutputSoapObject(request);
		httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		
    	try
    	{
    		
    		httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	    httpTransport.call(SOAP_ACTION, envelope);
    	    response = (SoapObject) envelope.getResponse();
    	    
    	}
    	catch (Exception exception)
    	{
    		throw exception;
    	}
    	
		return response;
		
	}

}
