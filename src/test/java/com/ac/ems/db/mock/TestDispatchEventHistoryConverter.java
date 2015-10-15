package com.ac.ems.db.mock;

import com.ac.ems.data.DispatchEventHistory;
import com.ac.ems.db.mongo.DispatchEventHistoryConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestDispatchEventHistoryConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    DispatchEventHistory origData = MockDataFactory.createTestDispatchEventHistory();
    
    DBObject transform1 = DispatchEventHistoryConverter.convertDispatchEventHistoryToMongo(MockDataFactory.createTestDispatchEventHistory());
    DispatchEventHistory finalData = DispatchEventHistoryConverter.convertMongoToDispatchEventHistory(transform1);
    
    assertTrue("The eventIDs are not equal", origData.getEventID() == finalData.getEventID());
    assertTrue("The dispatchIDs are not equal", origData.getDispatchID() == finalData.getDispatchID());
    assertTrue("The ambulanceIDs are not equal", origData.getAmbulanceID() == finalData.getAmbulanceID());
    assertTrue("The recommendedHospitalIDs are not equal", origData.getRecommendedHospitalID() == finalData.getRecommendedHospitalID());
    assertTrue("The targetHospitalIDs are not equal", origData.getTargetHospitalID() == finalData.getTargetHospitalID());
    assertTrue("The eventResolvedStates are not equal", origData.getEventResolvedState().compareTo(finalData.getEventResolvedState()) == 0);
    assertTrue("The eventStartDates are not equal", origData.getEventStartDate().compareTo(finalData.getEventStartDate()) == 0);
    assertTrue("The beginTransportDates are not equal", origData.getBeginTransportDate().compareTo(finalData.getBeginTransportDate()) == 0);
    assertTrue("The eventEndDates are not equal", origData.getEventEndDate().compareTo(finalData.getEventEndDate()) == 0);
  }
}
