package com.ac.ems.db.mongo;

import java.util.ArrayList;
import java.util.List;

import com.ac.ems.data.UserInformation;
import com.ac.ems.data.util.UserRoleConverter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author ac010168
 *
 */
public class UserInformationConverter {

  public static BasicDBObject convertIDToQuery(UserInformation user) {
    if (user == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", user.getUserID());
    return dbObject;
  }

  public static BasicDBObject convertIDToQuery(long userID) {
    if (userID < 0) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", userID);
    return dbObject;
  }
  
  public static BasicDBObject convertUserInformationToMongo(UserInformation user) {
    if (user == null) return null;
    
    BasicDBObject dbObject = new BasicDBObject("userID", user.getUserID());
    if (user.getPassword() != null)      dbObject.append("password", user.getPassword());
    if (user.getUserRole() != null)      dbObject.append("userRole", UserRoleConverter.convertUserRoleToString(user.getUserRole()));
    if (user.getAuthorizedIDs() != null) dbObject.append("authorizedIDs", convertList(user.getAuthorizedIDs()));

    return dbObject;
  }
  
  public static UserInformation convertMongoToUserInformation(DBObject dbObject) {
    if (dbObject == null) return null;
    
    UserInformation user = new UserInformation();
    if (dbObject.containsField("userID"))        user.setUserID((Long)dbObject.get("userID"));
    if (dbObject.containsField("password"))      user.setPassword((String)dbObject.get("password"));
    if (dbObject.containsField("userRole"))      user.setUserRole(UserRoleConverter.convertStringToUserRole((String)dbObject.get("userRole")));
    if (dbObject.containsField("authorizedIDs")) user.setAuthorizedIDs(convertDBListToLongList((BasicDBList)dbObject.get("authorizedIDs")));

    return user;
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
