package com.potekadesignio.sisbenapp_dev.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.util.Log;

import com.potekadesignio.sisbenapp_dev.BLL.BitacoraBO;
import com.potekadesignio.sisbenapp_dev.BLL.DimensionBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaCompletaBO;
import com.potekadesignio.sisbenapp_dev.BLL.OfertaSimpleBO;
import com.potekadesignio.sisbenapp_dev.BLL.UsuarioBO;

public class SaveObject_Helper {
	
	public void GuardarInfoUsuarioBO(UsuarioBO p, String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(p); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información del Usuario: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaUsuarioBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información del Usuario: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	public void GuardarInfoMOROfertasBO(OfertaSimpleBO osOfertasMOR[], String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(osOfertasMOR); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información de las Ofertas MOR: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaOfertasMOROfertasBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información de las Ofertas MOR: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	/* Oferta Personalizada */
	/*----------------------------------------------------------------------------------------------------------*/
	
	public void GuardarInfoOfertasBO(OfertaCompletaBO osOfertasUsuario[], String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(osOfertasUsuario); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información de las Ofertas: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaOfertasBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información de las Ofertas: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	/* Ofertas Agendadas */
	/*----------------------------------------------------------------------------------------------------------*/
	
	public void GuardarInfoOfertasAgendadasBO(ArrayList<OfertaCompletaBO> osOfertasUsuario, String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(osOfertasUsuario); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información de las Ofertas Agendadas: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaOfertasAgendadasBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información de las Ofertas Agendadas: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	/* Dimensiones */
	/*----------------------------------------------------------------------------------------------------------*/
	
	public void GuardarInfoDimensionesBO(DimensionBO diDimensiones[], String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(diDimensiones); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información de las Ofertas: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaDimensionBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información de las Ofertas: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	/* Bitacoras */
	/*----------------------------------------------------------------------------------------------------------*/
	
	public void GuardarInfoBitacorasBO(ArrayList<BitacoraBO> biBitacoras, String nombreArchivo)
	{
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nombreArchivo)));
           oos.writeObject(biBitacoras); 
           oos.flush(); 
           oos.close();
        }
        catch(Exception ex)
        {
           Log.v("Error al guardar en disco la información de las Bitácoras: ",ex.getMessage());
           ex.printStackTrace();
        }
	}
	
	public Object CargarInfoSerializadaBitacorasBO(File f)
	{
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
    	   Log.v("Error al leer la información de las Ofertas: ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
	}
	
	
	/*----------------------------------------------------------------------------------------------------------*/
	/* Eliminar Archivo */
	/*----------------------------------------------------------------------------------------------------------*/
	
	public void EliminarArchivoBO(File f)
	{
		try
		{
			f.delete();	
		}
		catch(Exception ex)
		{
			Log.v("Error al eliminar el archivo: " + f.getName() ,ex.getMessage());
			ex.printStackTrace();
		}
	}
	

}
