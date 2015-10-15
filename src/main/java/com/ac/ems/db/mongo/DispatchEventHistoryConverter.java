package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.DispatchEventHistory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the DispatchEventHistory class
 * 
 * @author ac010168
 *
 */
public class DispatchEventHistoryConverter {

  public static BasicDBObject convertIDToQuery(DispatchEventHistory event) {
    if (event == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", event.getEventID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long eventID) {
    if (eventID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", eventID);
    return dbObject;
  }
  
  public static BasicDBObject convertDispatchEventHistoryToMongo(DispatchEventHistory event) {
    if (event == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", event.getEventID());
    if (event.getDispatchID() != -1)            dbObject.append("dispatchID", event.getDispatchID());
    if (event.getAmbulanceID() != -1)           dbObject.append("ambulanceID", event.getAmbulanceID());
    if (event.getRecommendedHospitalID() != -1) dbObject.append("recommendedHospitalID", event.getRecommendedHospitalID());
    if (event.getTargetHospitalID() != -1)      dbObject.append("targetHospitalID", event.getTargetHospitalID());
    if (event.getEventResolvedState() != null)  dbObject.append("eventResolvedState", event.getEventResolvedState());
    if (event.getEventStartDate() != null)      dbObject.append("eventStartDate", event.getEventStartDate());
    if (event.getBeginTransportDate() != null)  dbObject.append("beginTransportDate", event.getBeginTransportDate());
    if (event.getEventEndDate() != null)        dbObject.append("eventEndDate", event.getEventEndDate());

    return dbObject;
  }
  
  public static DispatchEventHistory convertMongoToDispatchEventHistory(DBObject dbObject) {
    if (dbObject == null) return null;
    
    DispatchEventHistory event = new DispatchEventHistory();
    if (dbObject.containsField("eventID"))               event.setEventID((Long)dbObject.get("eventID"));
    if (dbObject.containsField("dispatchID"))            event.setDispatchID((Long)dbObject.get("dispatchID"));
    if (dbObject.containsField("ambulanceID"))           event.setAmbulanceID((Long)dbObject.get("ambulanceID"));
    if (dbObject.containsField("recommendedHospitalID")) event.setRecommendedHospitalID((Long)dbObject.get("recommendedHospitalID"));
    if (dbObject.containsField("targetHospitalID"))      event.setTargetHospitalID((Long)dbObject.get("targetHospitalID"));
    if (dbObject.containsField("eventResolvedState"))    event.setEventResolvedState((String)dbObject.get("eventResolvedState"));
    if (dbObject.containsField("eventStartDate"))        event.setEventStartDate((Date)dbObject.get("eventStartDate"));
    if (dbObject.containsField("beginTransportDate"))    event.setBeginTransportDate((Date)dbObject.get("beginTransportDate"));
    if (dbObject.containsField("eventEndDate"))          event.setEventEndDate((Date)dbObject.get("eventEndDate"));

    return event;
  }
}
