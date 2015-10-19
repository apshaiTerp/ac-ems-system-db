package com.ac.ems.db.mock;

import com.ac.ems.data.DispatchEventLog;
import com.ac.ems.db.mongo.DispatchEventLogConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestDispatchEventLogConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    DispatchEventLog origData = MockDataFactory.createTestDispatchEventLog();
    
    DBObject transform1 = DispatchEventLogConverter.convertDispatchEventLogToMongo(MockDataFactory.createTestDispatchEventLog());
    DispatchEventLog finalData = DispatchEventLogConverter.convertMongoToDispatchEventLog(transform1);
    
    assertTrue("The eventIDs are not equal", origData.getEventID() == finalData.getEventID());
    assertTrue("The curStates are not equal", origData.getCurState().compareTo(finalData.getCurState()) == 0);
    assertTrue("The changedOnDates are not equal", origData.getChangedOnDate().compareTo(finalData.getChangedOnDate()) == 0);
    assertTrue("The changedByUserIDs are not equal", origData.getChangedByUserID() == finalData.getChangedByUserID());
  }
}
