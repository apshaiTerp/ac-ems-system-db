package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.DispatchEvent;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the DispatchEvent class
 * 
 * @author ac010168
 *
 */
public class DispatchEventConverter {

  public static BasicDBObject convertIDToQuery(DispatchEvent event) {
    if (event == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", event.getEventID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long eventID) {
    if (eventID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", eventID);
    return dbObject;
  }
  
  public static BasicDBObject convertDispatchEventToMongo(DispatchEvent event) {
    if (event == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", event.getEventID());
    if (event.getDispatchID() != -1)            dbObject.append("dispatchID", event.getDispatchID());
    if (event.getAmbulanceID() != -1)           dbObject.append("ambulanceID", event.getAmbulanceID());
    if (event.getRecommendedHospitalID() != -1) dbObject.append("recommendedHospitalID", event.getRecommendedHospitalID());
    if (event.getTargetHospitalID() != -1)      dbObject.append("targetHospitalID", event.getTargetHospitalID());
    if (event.getEventState() != null)          dbObject.append("eventState", event.getEventState());
    if (event.getEventStartDate() != null)      dbObject.append("eventStartDate", event.getEventStartDate());
    if (event.getBeginTransportDate() != null)  dbObject.append("beginTransportDate", event.getBeginTransportDate());

    return dbObject;
  }
  
  public static DispatchEvent convertMongoToDispatchEvent(DBObject dbObject) {
    if (dbObject == null) return null;
    
    DispatchEvent event = new DispatchEvent();
    if (dbObject.containsField("eventID"))               event.setEventID((Long)dbObject.get("eventID"));
    if (dbObject.containsField("dispatchID"))            event.setDispatchID((Long)dbObject.get("dispatchID"));
    if (dbObject.containsField("ambulanceID"))           event.setAmbulanceID((Long)dbObject.get("ambulanceID"));
    if (dbObject.containsField("recommendedHospitalID")) event.setRecommendedHospitalID((Long)dbObject.get("recommendedHospitalID"));
    if (dbObject.containsField("targetHospitalID"))      event.setTargetHospitalID((Long)dbObject.get("targetHospitalID"));
    if (dbObject.containsField("eventState"))            event.setEventState((String)dbObject.get("eventState"));
    if (dbObject.containsField("eventStartDate"))        event.setEventStartDate((Date)dbObject.get("eventStartDate"));
    if (dbObject.containsField("beginTransportDate"))    event.setBeginTransportDate((Date)dbObject.get("beginTransportDate"));

    return event;
  }
}
