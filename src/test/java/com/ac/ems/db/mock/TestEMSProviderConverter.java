package com.ac.ems.db.mock;

import com.ac.ems.data.EMSProvider;
import com.ac.ems.db.mongo.EMSProviderConverter;
import com.mongodb.DBObject;

import junit.framework.TestCase;

/**
 * @author ac010168
 *
 */
public class TestEMSProviderConverter extends TestCase {

  /**
   * A simple test designed to show that the mongo conversion process maps correctly.
   */
  public void testSimpleConversion() {
    EMSProvider origData = MockDataFactory.createTestEMSProvider();
    
    DBObject transform1 = EMSProviderConverter.convertEMSProviderToMongo(MockDataFactory.createTestEMSProvider());
    EMSProvider finalData = EMSProviderConverter.convertMongoToEMSProvider(transform1);
    
    assertTrue("The providerIDs are not equal", origData.getProviderID() == finalData.getProviderID());
    assertTrue("The providerNames are not equal", origData.getProviderName().compareTo(finalData.getProviderName()) == 0);
    assertTrue("The providerLats are not equal", origData.getProviderLat() == finalData.getProviderLat());
    assertTrue("The providerLons are not equal", origData.getProviderLon() == finalData.getProviderLon());
    
    assertTrue("The number of ambulances are not equal", origData.getAmbulances().size() == finalData.getAmbulances().size());
    assertTrue("The number of availAmbulances are not equal", origData.getAvailAmbulances().size() == finalData.getAvailAmbulances().size());
    assertTrue("The number of assignedAmbulances are not equal", origData.getAssignedAmbulances().size() == finalData.getAssignedAmbulances().size());
    
    for (Long curAmbulance : origData.getAmbulances())
      assertTrue("I could not find ambulance " + curAmbulance, finalData.getAmbulances().contains(curAmbulance));
    for (Long curAmbulance : origData.getAvailAmbulances())
      assertTrue("I could not find availAmbulance " + curAmbulance, finalData.getAvailAmbulances().contains(curAmbulance));
    for (Long curAmbulance : origData.getAssignedAmbulances())
      assertTrue("I could not find assignedAmbulance " + curAmbulance, finalData.getAssignedAmbulances().contains(curAmbulance));
  }
}
