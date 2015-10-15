package com.ac.ems.db.mock;

import com.ac.ems.data.User;
import com.ac.ems.db.mongo.UserConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestUserConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    User origData = MockDataFactory.createTestUser();
    
    DBObject transform1 = UserConverter.convertUserToMongo(MockDataFactory.createTestUser());
    User finalData = UserConverter.convertMongoToUser(transform1);
    
    assertTrue("The userIDs are not equal", origData.getUserID() == finalData.getUserID());
    assertTrue("The userNames are not equal", origData.getUserName().compareTo(finalData.getUserName()) == 0);
    assertTrue("The userNameDisplays are not equal", origData.getUserNameDisplay().compareTo(finalData.getUserNameDisplay()) == 0);
  }
}
