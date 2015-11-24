package com.ac.ems.db.mock;

import junit.framework.TestCase;

import com.ac.ems.data.HospitalDiversionHistory;
import com.ac.ems.db.mongo.HospitalDiversionHistoryConverter;
import com.mongodb.DBObject;

/**
 * @author ac010168
 *
 */
public class TestHospitalDiversionHistoryConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    HospitalDiversionHistory origData = MockDataFactory.createTestHospitalDiversionHistory();
    
    DBObject transform1 = HospitalDiversionHistoryConverter.convertHospitalDiversionHistoryToMongo(MockDataFactory.createTestHospitalDiversionHistory());
    HospitalDiversionHistory finalData = HospitalDiversionHistoryConverter.convertMongoToHospitalDiversionHistory(transform1);
    
    assertTrue("The diversionIDs are not equal", origData.getDiversionID() == finalData.getDiversionID());
    assertTrue("The hospitalIDs are not equal", origData.getHospitalID() == finalData.getHospitalID());
    assertTrue("The divertCategories are not equal", origData.getDivertCategory().compareTo(finalData.getDivertCategory()) == 0);
    assertTrue("The curStates are not equal", origData.getCurState().compareTo(finalData.getCurState()) == 0);
    assertTrue("The changedOnDates are not equal", origData.getChangedOnDate().compareTo(finalData.getChangedOnDate()) == 0);
    assertTrue("The changedByUserIDs are not equal", origData.getChangedByUserID() == finalData.getChangedByUserID());
  }
}
