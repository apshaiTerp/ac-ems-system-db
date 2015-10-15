package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.AmbulanceTravelHistory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the AmbulanceTravelHistory class
 * 
 * @author ac010168
 *
 */
public class AmbulanceTravelHistoryConverter {

  public static BasicDBObject convertIDToQuery(AmbulanceTravelHistory ambulance) {
    if (ambulance == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulance.getAmbulanceID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long ambulanceID) {
    if (ambulanceID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulanceID);
    return dbObject;
  }
  
  public static BasicDBObject convertAmbulanceTravelHistoryToMongo(AmbulanceTravelHistory ambulance) {
    if (ambulance == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("ambulanceID", ambulance.getAmbulanceID());
    if (ambulance.getEventID() != -1)           dbObject.append("eventID", ambulance.getEventID());
    if (ambulance.getAmbLat() != 0.0)           dbObject.append("ambLat", ambulance.getAmbLat());
    if (ambulance.getAmbLon() != 0.0)           dbObject.append("ambLon", ambulance.getAmbLon());
    if (ambulance.getRecordedDate() != null)    dbObject.append("recordedDate", ambulance.getRecordedDate());

    return dbObject;
  }
  
  public static AmbulanceTravelHistory convertMongoToAmbulanceTravelHistory(DBObject dbObject) {
    if (dbObject == null) return null;
    
    AmbulanceTravelHistory ambulance = new AmbulanceTravelHistory();
    if (dbObject.containsField("ambulanceID"))     ambulance.setAmbulanceID((Long)dbObject.get("ambulanceID"));
    if (dbObject.containsField("eventID"))         ambulance.setEventID((Long)dbObject.get("eventID"));
    if (dbObject.containsField("ambLat"))          ambulance.setAmbLat((Double)dbObject.get("ambLat"));
    if (dbObject.containsField("ambLon"))          ambulance.setAmbLon((Double)dbObject.get("ambLon"));
    if (dbObject.containsField("recordedDate"))    ambulance.setRecordedDate((Date)dbObject.get("recordedDate"));

    return ambulance;
  }
}
