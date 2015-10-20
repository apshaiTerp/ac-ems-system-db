package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.DispatchDetails;
import com.ac.ems.data.enums.SeverityLevelConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the DispatchDetails class
 * 
 * @author ac010168
 *
 */
public class DispatchDetailsConverter {

  public static BasicDBObject convertIDToQuery(DispatchDetails dispatch) {
    if (dispatch == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("dispatchID", dispatch.getDispatchID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long dispatchID) {
    if (dispatchID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("dispatchID", dispatchID);
    return dbObject;
  }
  
  public static BasicDBObject convertDispatchDetailsToMongo(DispatchDetails dispatch) {
    if (dispatch == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("dispatchID", dispatch.getDispatchID());
    if (dispatch.getPatientName() != null)          dbObject.append("patientName", dispatch.getPatientName());
    if (dispatch.getPatientAddress() != null)       dbObject.append("patientAddress", dispatch.getPatientAddress());
    if (dispatch.getPatientGender() != null)        dbObject.append("patientGender", dispatch.getPatientGender());
    if (dispatch.getPatientAgeRange() != null)      dbObject.append("patientAgeRange", dispatch.getPatientAgeRange());
    if (dispatch.getPatientComplaint() != null)     dbObject.append("patientComplaint", dispatch.getPatientComplaint());
    if (dispatch.getReportedByName() != null)       dbObject.append("reportedByName", dispatch.getReportedByName());
    if (dispatch.getReportedSeverity() != null)     dbObject.append("reportedSeverity", SeverityLevelConverter.convertSeverityToString(dispatch.getReportedSeverity()));
    if (dispatch.getDispatchUserID() != -1)         dbObject.append("dispatchUserID", dispatch.getDispatchUserID());
    if (dispatch.getDispatchReceivedDate() != null) dbObject.append("dispatchReceivedDate", dispatch.getDispatchReceivedDate());

    return dbObject;
  }
  
  public static DispatchDetails convertMongoToDispatchDetails(DBObject dbObject) {
    if (dbObject == null) return null;
    
    DispatchDetails dispatch = new DispatchDetails();
    if (dbObject.containsField("dispatchID"))           dispatch.setDispatchID((Long)dbObject.get("dispatchID"));
    if (dbObject.containsField("patientName"))          dispatch.setPatientName((String)dbObject.get("patientName"));
    if (dbObject.containsField("patientAddress"))       dispatch.setPatientAddress((String)dbObject.get("patientAddress"));
    if (dbObject.containsField("patientGender"))        dispatch.setPatientGender((String)dbObject.get("patientGender"));
    if (dbObject.containsField("patientAgeRange"))      dispatch.setPatientAgeRange((String)dbObject.get("patientAgeRange"));
    if (dbObject.containsField("patientComplaint"))     dispatch.setPatientComplaint((String)dbObject.get("patientComplaint"));
    if (dbObject.containsField("reportedByName"))       dispatch.setReportedByName((String)dbObject.get("reportedByName"));
    if (dbObject.containsField("reportedSeverity"))     dispatch.setReportedSeverity(SeverityLevelConverter.convertStringToSeverity((String)dbObject.get("reportedSeverity")));
    if (dbObject.containsField("dispatchUserID"))       dispatch.setDispatchUserID((Long)dbObject.get("dispatchUserID"));
    if (dbObject.containsField("dispatchReceivedDate")) dispatch.setDispatchReceivedDate((Date)dbObject.get("dispatchReceivedDate"));

    return dispatch;
  }
}
