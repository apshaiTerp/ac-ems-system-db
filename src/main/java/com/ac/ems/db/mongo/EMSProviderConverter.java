package com.ac.ems.db.mongo;

import java.util.ArrayList;
import java.util.List;

import com.ac.ems.data.EMSProvider;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the EMSProvider class
 * 
 * @author ac010168
 *
 */
public class EMSProviderConverter {

  public static BasicDBObject convertIDToQuery(EMSProvider provider) {
    if (provider == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("providerID", provider.getProviderID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long providerID) {
    if (providerID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("providerID", providerID);
    return dbObject;
  }
  
  public static BasicDBObject convertEMSProviderToMongo(EMSProvider provider) {
    if (provider == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("providerID", provider.getProviderID());
    if (provider.getProviderID() != -1)           dbObject.append("providerID", provider.getProviderID());
    if (provider.getProviderName() != null)       dbObject.append("providerName", provider.getProviderName());
    if (provider.getProviderLat() != 0.0)         dbObject.append("providerLat", provider.getProviderLat());
    if (provider.getProviderLon() != 0.0)         dbObject.append("providerLon", provider.getProviderLon());
    
    if (provider.getAmbulances() != null)         dbObject.append("ambulances", convertList(provider.getAmbulances()));
    if (provider.getAvailAmbulances() != null)    dbObject.append("availAmbulances", convertList(provider.getAvailAmbulances()));
    if (provider.getAssignedAmbulances() != null) dbObject.append("assignedAmbulances", convertList(provider.getAssignedAmbulances()));
    
    return dbObject;
  }
  
  public static EMSProvider convertMongoToEMSProvider(DBObject dbObject) {
    if (dbObject == null) return null;
    
    EMSProvider provider = new EMSProvider();
    if (dbObject.containsField("providerID"))         provider.setProviderID((Long)dbObject.get("providerID"));
    if (dbObject.containsField("providerName"))       provider.setProviderName((String)dbObject.get("providerName"));
    if (dbObject.containsField("providerLat"))        provider.setProviderLat((Double)dbObject.get("providerLat"));
    if (dbObject.containsField("providerLon"))        provider.setProviderLon((Double)dbObject.get("providerLon"));

    if (dbObject.containsField("ambulances"))         provider.setAmbulances(convertDBListToLongList((BasicDBList)dbObject.get("ambulances")));
    if (dbObject.containsField("availAmbulances"))    provider.setAvailAmbulances(convertDBListToLongList((BasicDBList)dbObject.get("availAmbulances")));
    if (dbObject.containsField("assignedAmbulances")) provider.setAssignedAmbulances(convertDBListToLongList((BasicDBList)dbObject.get("assignedAmbulances")));

    return provider;
  }

  /**
   * Helper method to parse Lists into List format for Mongo.  Parameterized as <?> to
   * allow for generic mapping, provided those objects are simple objects.
   * 
   * @param curList The List of elements (not null) to be converted into an array.
   * @return A new list in BasicDBList format.
   */
  private static BasicDBList convertList(List<?> curList) {
    if (curList == null) return null;
    
    BasicDBList newList = new BasicDBList();
    for (Object obj : curList)
      newList.add(obj);
    return newList;
  }
  
  /**
   * Helper method to parse Lists from Mongo into Java.  
   * 
   * @param curList The List of elements (not null) to be converted into an array.
   * @return A new list in List<Long> format.
   */
  private static List<Long> convertDBListToLongList(BasicDBList curList) {
    if (curList == null) return null;
    
    List<Long> newList = new ArrayList<Long>(curList.size());
    for (Object obj : curList) {
      newList.add((Long)obj);
    }
    return newList;
  }
}
