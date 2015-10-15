package com.ac.ems.db.mock;

import com.ac.ems.data.Hospital;
import com.ac.ems.db.mongo.HospitalConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestHospitalConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    Hospital origData = MockDataFactory.createTestHospital();
    
    DBObject transform1 = HospitalConverter.convertHospitalToMongo(MockDataFactory.createTestHospital());
    Hospital finalData = HospitalConverter.convertMongoToHospital(transform1);
    
    assertTrue("The hospitalIDs are not equal", origData.getHospitalID() == finalData.getHospitalID());
    assertTrue("The hospitalNames are not equal", origData.getHospitalName().compareTo(finalData.getHospitalName()) == 0);
    assertTrue("The hospitalLats are not equal", origData.getHospitalLat() == finalData.getHospitalLat());
    assertTrue("The hospitalLons are not equal", origData.getHospitalLon() == finalData.getHospitalLon());
    assertTrue("The addresses are not equal", origData.getAddress().compareTo(finalData.getAddress()) == 0);
    
    assertTrue("The traumaBeds are not equal", origData.getTraumaBeds() == finalData.getTraumaBeds());
    assertTrue("The traumaBedsFrees are not equal", origData.getTraumaBedsFree() == finalData.getTraumaBedsFree());
    assertTrue("The traumaBedsOccupieds are not equal", origData.getTraumaBedsOccupied() == finalData.getTraumaBedsOccupied());
    assertTrue("The traumaBedsCleanups are not equal", origData.getTraumaBedsCleanup() == finalData.getTraumaBedsCleanup());

    assertTrue("The erBeds are not equal", origData.getErBeds() == finalData.getErBeds());
    assertTrue("The erBedsFrees are not equal", origData.getErBedsFree() == finalData.getErBedsFree());
    assertTrue("The erBedsOccupieds are not equal", origData.getErBedsOccupied() == finalData.getErBedsOccupied());
    assertTrue("The erBedsCleanups are not equal", origData.getErBedsCleanup() == finalData.getErBedsCleanup());

    assertTrue("The erDiverts are not equal", origData.getErDivert().compareTo(finalData.getErDivert()) == 0);
    assertTrue("The traumaDiverts are not equal", origData.getTraumaDivert().compareTo(finalData.getTraumaDivert()) == 0);
    assertTrue("The burnDiverts are not equal", origData.getBurnDivert().compareTo(finalData.getBurnDivert()) == 0);
    assertTrue("The stemiDiverts are not equal", origData.getStemiDivert().compareTo(finalData.getBurnDivert()) == 0);
    assertTrue("The strokeDiverts are not equal", origData.getStrokeDivert().compareTo(finalData.getStrokeDivert()) == 0);
    
    assertTrue("The levelOfCares are not the same length", origData.getLevelOfCare().length == finalData.getLevelOfCare().length);
    assertTrue("The hostAges are not the same length", origData.getHostAges().length == finalData.getHostAges().length);
    
    for (String levelOfCare : origData.getLevelOfCare()) {
      boolean found = false;
      for (String hasLevelOfCare : finalData.getLevelOfCare()) {
        if (levelOfCare.equalsIgnoreCase(hasLevelOfCare)) {
          found = true;
          break;
        }
      }
      if (!found) {
        fail("I could not find levelOfCare '" + levelOfCare + "' in the final List");
      }
    }
    for (String hostAge : origData.getHostAges()) {
      boolean found = false;
      for (String hasHostAge : finalData.getHostAges()) {
        if (hostAge.equalsIgnoreCase(hasHostAge)) {
          found = true;
          break;
        }
      }
      if (!found) {
        fail("I could not find hostAge '" + hostAge + "' in the final List");
      }
    }
    
  }
}
