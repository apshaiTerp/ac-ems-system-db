package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.Ambulance;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the Ambulance class
 * 
 * @author ac010168
 *
 */
public class AmbulanceConverter {

  public static BasicDBObject convertIDTOQuery(Ambulance ambulance) {
    if (ambulance == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulance.getAmbulanceID());
    return dbObject;
  }

  public static BasicDBObject convertIDTOQuery(long ambulanceID) {
    if (ambulanceID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulanceID);
    return dbObject;
  }
  
  public static BasicDBObject convertAmbulanceToMongo(Ambulance ambulance) {
    if (ambulance == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulance.getAmbulanceID());
    if (ambulance.getProviderID() != -1)        dbObject.append("providerID", ambulance.getProviderID());
    if (ambulance.getAmbLat() != 0.0)           dbObject.append("ambLat", ambulance.getAmbLat());
    if (ambulance.getAmbLon() != 0.0)           dbObject.append("ambLon", ambulance.getAmbLon());
    if (ambulance.getLastUpdate() != null)      dbObject.append("lastUpdate", ambulance.getLastUpdate());

    return dbObject;
  }
  
  public static Ambulance convertMongoToAmbulance(DBObject dbObject) {
    if (dbObject == null) return null;
    
    Ambulance ambulance = new Ambulance();
    if (dbObject.containsField("ambulanceID"))     ambulance.setAmbulanceID((Long)dbObject.get("ambulanceID"));
    if (dbObject.containsField("providerID"))      ambulance.setProviderID((Long)dbObject.get("providerID"));
    if (dbObject.containsField("ambLat"))          ambulance.setAmbLat((Double)dbObject.get("ambLat"));
    if (dbObject.containsField("ambLon"))          ambulance.setAmbLon((Double)dbObject.get("ambLon"));
    if (dbObject.containsField("lastUpdate"))      ambulance.setLastUpdate((Date)dbObject.get("lastUpdate"));

    return ambulance;
  }
}
