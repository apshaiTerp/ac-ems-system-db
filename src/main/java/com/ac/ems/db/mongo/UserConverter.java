package com.ac.ems.db.mongo;

import com.ac.ems.data.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Mongo Conversion tools for the User class
 * 
 * @author ac010168
 *
 */
public class UserConverter {

  public static BasicDBObject convertIDToQuery(User user) {
    if (user == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", user.getUserID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long userID) {
    if (userID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", userID);
    return dbObject;
  }
  
  public static BasicDBObject convertUserToMongo(User user) {
    if (user == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", user.getUserID());
    if (user.getUserName() != null)        dbObject.append("userName", user.getUserName());
    if (user.getUserNameDisplay() != null) dbObject.append("userNameDisplay", user.getUserNameDisplay());

    return dbObject;
  }
  
  public static User convertMongoToUser(DBObject dbObject) {
    if (dbObject == null) return null;
    
    User user = new User();
    if (dbObject.containsField("userID"))          user.setUserID((Long)dbObject.get("userID"));
    if (dbObject.containsField("userName"))        user.setUserName((String)dbObject.get("userName"));
    if (dbObject.containsField("userNameDisplay")) user.setUserNameDisplay((String)dbObject.get("userNameDisplay"));

    return user;
  }
}
