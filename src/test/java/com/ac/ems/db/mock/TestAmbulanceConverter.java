package com.ac.ems.db.mock;

import com.ac.ems.data.Ambulance;
import com.ac.ems.db.mongo.AmbulanceConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestAmbulanceConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    Ambulance origData = MockDataFactory.createTestAmbulance();
    
    DBObject transform1 = AmbulanceConverter.convertAmbulanceToMongo(MockDataFactory.createTestAmbulance());
    Ambulance finalData = AmbulanceConverter.convertMongoToAmbulance(transform1);
    
    assertTrue("The ambulanceIDs are not equal", origData.getAmbulanceID() == finalData.getAmbulanceID());
    assertTrue("The providerIDs are not equal", origData.getProviderID() == finalData.getProviderID());
    assertTrue("The ambLats are not equal", origData.getAmbLat() == finalData.getAmbLat());
    assertTrue("The ambLons are not equal", origData.getAmbLon() == finalData.getAmbLon());
    assertTrue("The lastUpdates are not equal", origData.getLastUpdate().compareTo(finalData.getLastUpdate()) == 0);
  }
}
