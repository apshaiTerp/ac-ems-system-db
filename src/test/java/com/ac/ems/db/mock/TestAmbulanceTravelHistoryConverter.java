package com.ac.ems.db.mock;

import com.ac.ems.data.AmbulanceTravelHistory;
import com.ac.ems.db.mongo.AmbulanceTravelHistoryConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestAmbulanceTravelHistoryConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    AmbulanceTravelHistory origData = MockDataFactory.createTestAmbulanceTravelHistory();
    
    DBObject transform1 = AmbulanceTravelHistoryConverter.convertAmbulanceTravelHistoryToMongo(MockDataFactory.createTestAmbulanceTravelHistory());
    AmbulanceTravelHistory finalData = AmbulanceTravelHistoryConverter.convertMongoToAmbulanceTravelHistory(transform1);
    
    assertTrue("The ambulanceIDs are not equal", origData.getAmbulanceID() == finalData.getAmbulanceID());
    assertTrue("The providerIDs are not equal", origData.getEventID() == finalData.getEventID());
    assertTrue("The ambLats are not equal", origData.getAmbLat() == finalData.getAmbLat());
    assertTrue("The ambLons are not equal", origData.getAmbLon() == finalData.getAmbLon());
    assertTrue("The lastUpdates are not equal", origData.getRecordedDate().compareTo(finalData.getRecordedDate()) == 0);
  }
}
