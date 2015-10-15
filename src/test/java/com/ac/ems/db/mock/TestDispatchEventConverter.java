package com.ac.ems.db.mock;

import com.ac.ems.data.DispatchEvent;
import com.ac.ems.db.mongo.DispatchEventConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestDispatchEventConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    DispatchEvent origData = MockDataFactory.createTestDispatchEvent();
    
    DBObject transform1 = DispatchEventConverter.convertDispatchEventToMongo(MockDataFactory.createTestDispatchEvent());
    DispatchEvent finalData = DispatchEventConverter.convertMongoToDispatchEvent(transform1);
    
    assertTrue("The eventIDs are not equal", origData.getEventID() == finalData.getEventID());
    assertTrue("The dispatchIDs are not equal", origData.getDispatchID() == finalData.getDispatchID());
    assertTrue("The ambulanceIDs are not equal", origData.getAmbulanceID() == finalData.getAmbulanceID());
    assertTrue("The recommendedHospitalIDs are not equal", origData.getRecommendedHospitalID() == finalData.getRecommendedHospitalID());
    assertTrue("The targetHospitalIDs are not equal", origData.getTargetHospitalID() == finalData.getTargetHospitalID());
    assertTrue("The eventStates are not equal", origData.getEventState().compareTo(finalData.getEventState()) == 0);
    assertTrue("The eventStartDates are not equal", origData.getEventStartDate().compareTo(finalData.getEventStartDate()) == 0);
    assertTrue("The beginTransportDates are not equal", origData.getBeginTransportDate().compareTo(finalData.getBeginTransportDate()) == 0);
  }
}
