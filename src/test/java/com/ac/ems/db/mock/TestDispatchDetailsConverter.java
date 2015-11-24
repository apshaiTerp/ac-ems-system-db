package com.ac.ems.db.mock;

import com.ac.ems.data.DispatchDetails;
import com.ac.ems.db.mongo.DispatchDetailsConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestDispatchDetailsConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    DispatchDetails origData = MockDataFactory.createTestDispatchDetails();
    
    DBObject transform1 = DispatchDetailsConverter.convertDispatchDetailsToMongo(MockDataFactory.createTestDispatchDetails());
    DispatchDetails finalData = DispatchDetailsConverter.convertMongoToDispatchDetails(transform1);
    
    assertTrue("The dispatchIDs are not equal", origData.getDispatchID() == finalData.getDispatchID());
    assertTrue("The patientNames are not equal", origData.getPatientName().compareTo(finalData.getPatientName()) == 0);
    assertTrue("The patientAddresses are not equal", origData.getPatientAddress().compareTo(finalData.getPatientAddress()) == 0);
    assertTrue("The patientGenders are not equal", origData.getPatientGender().compareTo(finalData.getPatientGender()) == 0);
    assertTrue("The patientAgeRanges are not equal", origData.getPatientAgeRange().compareTo(finalData.getPatientAgeRange()) == 0);
    assertTrue("The patientComplaints are not equal", origData.getPatientComplaint().compareTo(finalData.getPatientComplaint()) == 0);
    assertTrue("The reportedByNames are not equal", origData.getReportedByName().compareTo(finalData.getReportedByName()) == 0);
    assertTrue("The reportedSeverities are not equal", origData.getReportedSeverity() == finalData.getReportedSeverity());
    assertTrue("The dispatchUserIDs are not equal", origData.getDispatchUserID() == finalData.getDispatchUserID());
    assertTrue("The dispatchReceivedDate are not equal", origData.getDispatchReceivedDate().compareTo(finalData.getDispatchReceivedDate()) == 0);
    assertTrue("The providerIDs are not equal", origData.getProviderID() == finalData.getProviderID());
    assertTrue("The incidentLats are not equal", origData.getIncidentLat() == finalData.getIncidentLat());
    assertTrue("The incidentLons are not equal", origData.getIncidentLon() == finalData.getIncidentLon());
  }
}
