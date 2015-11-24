package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.HospitalDiversionHistory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author ac010168
 *
 */
public class HospitalDiversionHistoryConverter {

  public static BasicDBObject convertIDToQuery(HospitalDiversionHistory diversion) {
    if (diversion == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("diversionID", diversion.getDiversionID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long diversionID) {
    if (diversionID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("diversionID", diversionID);
    return dbObject;
  }
  
  public static BasicDBObject convertHospitalDiversionHistoryToMongo(HospitalDiversionHistory diversion) {
    if (diversion == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("diversionID", diversion.getDiversionID());
    if (diversion.getHospitalID() != -1)       dbObject.append("hospitalID", diversion.getHospitalID());
    if (diversion.getDivertCategory() != null) dbObject.append("divertCategory", diversion.getDivertCategory());
    if (diversion.getCurState() != null)       dbObject.append("curState", diversion.getCurState());
    if (diversion.getChangedOnDate() != null)  dbObject.append("changedOnDate", diversion.getChangedOnDate());
    if (diversion.getChangedByUserID() != -1)  dbObject.append("changedByUserID", diversion.getChangedByUserID());

    return dbObject;
  }
  
  public static HospitalDiversionHistory convertMongoToHospitalDiversionHistory(DBObject dbObject) {
    if (dbObject == null) return null;
    
    HospitalDiversionHistory diversion = new HospitalDiversionHistory();
    if (dbObject.containsField("diversionID"))     diversion.setDiversionID((Long)dbObject.get("diversionID"));
    if (dbObject.containsField("hospitalID"))      diversion.setHospitalID((Long)dbObject.get("hospitalID"));
    if (dbObject.containsField("divertCategory"))  diversion.setDivertCategory((String)dbObject.get("divertCategory"));
    if (dbObject.containsField("curState"))        diversion.setCurState((String)dbObject.get("curState"));
    if (dbObject.containsField("changedOnDate"))   diversion.setChangedOnDate((Date)dbObject.get("changedOnDate"));
    if (dbObject.containsField("changedByUserID")) diversion.setChangedByUserID((Long)dbObject.get("changedByUserID"));

    return diversion;
  }
}
