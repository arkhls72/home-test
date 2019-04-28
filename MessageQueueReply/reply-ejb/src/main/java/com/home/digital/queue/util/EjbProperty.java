
package com.home.digital.queue.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

public final class EjbProperty {	
	public static final String EJB_PROPERTY_FILE="ejb.properties";

	private static Map<Property,String> map = new HashMap<Property,String>();
	
    /** Local reference to the singleton instance */
    private static volatile EjbProperty singleton = null;

    private static Properties prop = null;

    private static EjbProperty getInstance() throws Exception {
        EjbProperty singletonCopy = singleton;
        if (singletonCopy == null) {
            singletonCopy = singleton;
            if (singletonCopy == null) {
                singleton = singletonCopy = new EjbProperty();
            }
        }

        return singleton;
    }

    private EjbProperty() throws Exception {
    	initPropertyFile();
    }

    private  void initPropertyFile() throws Exception {
    	 try {
             prop = new Properties();
             InputStream in = this.getClass().getClassLoader().getResourceAsStream(EJB_PROPERTY_FILE);
             prop.load(in);
             in.close();

             if (CollectionUtils.isEmpty(prop.stringPropertyNames())) 
            	 throw new Exception("empty prop");

             for(String item : prop.stringPropertyNames()) {
            	 Property key = Property.pasre(item);
            	  if (key !=null) {
            		  map.put(key, prop.getProperty(item));
            	  }
            }

         } catch (Exception e) {

         }
    }
    
    public static Map<Property,String>  getProeprties() throws Exception {
    	if (MapUtils.isEmpty(map)) 
    		getInstance().initPropertyFile();

    	return map;
    }
    
    public static String getProperty(Property key) throws Exception  {
    	if (key == null)
    		return null;

    	if (MapUtils.isEmpty(map))
    		getInstance().initPropertyFile();	

    	return map.get(key);
    }
}
