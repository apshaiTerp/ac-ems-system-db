package com.ac.ems.db.mongo;

import java.util.Date;

import com.ac.ems.data.DispatchEventLog;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author ac010168
 *
 */
public class DispatchEventLogConverter {

  public static BasicDBObject convertIDToQuery(DispatchEventLog log) {
    if (log == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", log.getEventID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long eventID) {
    if (eventID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", eventID);
    return dbObject;
  }
  
  public static BasicDBObject convertDispatchEventLogToMongo(DispatchEventLog log) {
    if (log == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("eventID", log.getEventID());
    if (log.getCurState() != null)      dbObject.append("curState", log.getCurState());
    if (log.getChangedOnDate() != null) dbObject.append("changedOnDate", log.getChangedOnDate());
    if (log.getChangedByUserID() != -1) dbObject.append("changedByUserID", log.getChangedByUserID());

    return dbObject;
  }
  
  public static DispatchEventLog convertMongoToDispatchEventLog(DBObject dbObject) {
    if (dbObject == null) return null;
    
    DispatchEventLog log = new DispatchEventLog();
    if (dbObject.containsField("eventID"))         log.setEventID((Long)dbObject.get("eventID"));
    if (dbObject.containsField("curState"))        log.setCurState((String)dbObject.get("curState"));
    if (dbObject.containsField("changedOnDate"))   log.setChangedOnDate((Date)dbObject.get("changedOnDate"));
    if (dbObject.containsField("changedByUserID")) log.setChangedByUserID((Long)dbObject.get("changedByUserID"));

    return log;
  }
}
