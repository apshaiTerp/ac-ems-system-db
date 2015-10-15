package com.ac.ems.db.mock;

import com.ac.ems.data.UserInformation;
import com.ac.ems.db.mongo.UserInformationConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestUserInformationConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    UserInformation origData = MockDataFactory.createTestUserInformation();
    
    DBObject transform1 = UserInformationConverter.convertUserInformationToMongo(MockDataFactory.createTestUserInformation());
    UserInformation finalData = UserInformationConverter.convertMongoToUserInformation(transform1);
    assertTrue("The userIDs are not equal", origData.getUserID() == finalData.getUserID());
    assertTrue("The passswords are not equal", origData.getPassword().compareTo(finalData.getPassword()) == 0);
    assertTrue("The userRoles are not equal", origData.getUserRole() == finalData.getUserRole());
    
    assertTrue("The number of authorizedIDs are not equal", origData.getAuthorizedIDs().size() == finalData.getAuthorizedIDs().size());

    for (Long curAuthID : origData.getAuthorizedIDs())
      assertTrue("I could not find authorizedID " + curAuthID, finalData.getAuthorizedIDs().contains(curAuthID));
  }
}
