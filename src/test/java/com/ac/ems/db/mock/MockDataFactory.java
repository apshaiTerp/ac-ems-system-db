package com.ac.ems.db.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ac.ems.data.*;
import com.ac.ems.data.enums.SeverityLevel;
import com.ac.ems.data.enums.UserRole;

/**
 * A simple test class that allows me to create a couple of common objects for testing.
 * 
 * @author ac010168
 *
 */
public class MockDataFactory {

  public static Ambulance createTestAmbulance() {
    Ambulance testData = new Ambulance();
    
    testData.setAmbulanceID(200701L);
    testData.setProviderID(200702L);
    testData.setAmbLat(10.00005);
    testData.setAmbLon(20.00007);
    testData.setLastUpdate(new Date(1000000000));
    return testData;
  }

  public static AmbulanceTravelHistory createTestAmbulanceTravelHistory() {
    AmbulanceTravelHistory testData = new AmbulanceTravelHistory();
    
    testData.setAmbulanceID(200701L);
    testData.setEventID(20702L);
    testData.setAmbLat(11.00005);
    testData.setAmbLon(22.00008);
    testData.setRecordedDate(new Date(1500000000));
    
    return testData;
  }

  public static DispatchDetails createTestDispatchDetails() {
    DispatchDetails testData = new DispatchDetails();
    
    testData.setDispatchID(1004002L);
    testData.setPatientName("John Doe");
    testData.setPatientAddress("555 Nowhere Rd");
    testData.setPatientGender("M");
    testData.setPatientAgeRange("adult");
    testData.setPatientComplaint("It hurts");
    testData.setReportedByName("Hi, my name is");
    testData.setReportedSeverity(SeverityLevel.NONCRITICAL);
    testData.setDispatchUserID(234516L);
    testData.setDispatchReceivedDate(new Date(2000000000));
    testData.setProviderID(81826348768L);
    testData.setIncidentLat(32.58758765);
    testData.setIncidentLon(-92.876857647);
    
    return testData;
  }

  public static DispatchEvent createTestDispatchEvent() {
    DispatchEvent testData = new DispatchEvent();
    
    testData.setEventID(123099345L);
    testData.setDispatchID(1023871983745L);
    testData.setAmbulanceID(123857108L);
    testData.setRecommendedHospitalID(239875198347L);
    testData.setTargetHospitalID(8123087658176L);
    testData.setEventState("STATE");
    testData.setEventStartDate(new Date(1368716289374L));
    testData.setBeginTransportDate(new Date(87698761283L));
    testData.setActualAgeRange("teen");
    testData.setObservedSeverity(SeverityLevel.PEDTRAUMA);
    
    return testData;
  }

  public static DispatchEventHistory createTestDispatchEventHistory() {
    DispatchEventHistory testData = new DispatchEventHistory();
    
    testData.setEventID(128375091878L);
    testData.setDispatchID(98192873694871L);
    testData.setAmbulanceID(234576871634L);
    testData.setRecommendedHospitalID(1287365897163245L);
    testData.setTargetHospitalID(1112387686L);
    testData.setEventResolvedState("STATE");
    testData.setEventStartDate(new Date(918273098179082375L));
    testData.setBeginTransportDate(new Date(1384765981736489756L));
    testData.setActualAgeRange("child");
    testData.setObservedSeverity(SeverityLevel.PEDNONCRITICAL);
    testData.setEventEndDate(new Date(716293876918762L));
    
    return testData;
  }
  
  public static DispatchEventLog createTestDispatchEventLog() {
    DispatchEventLog testData = new DispatchEventLog();
    
    testData.setEventID(1832658176234857L);
    testData.setCurState("NEBRASKA");
    testData.setChangeDescription("This is where some stuff happened.");
    testData.setChangedOnDate(new Date(181276358716287L));
    testData.setChangedByUserID(123587162398756L);
    
    return testData;
  }

  public static EMSProvider createTestEMSProvider() {
    EMSProvider testData = new EMSProvider();
    
    testData.setProviderID(123094345234L);
    testData.setProviderName("JUnit EMS");
    testData.setProviderAddress("Somewhere over the Rainbow");
    testData.setProviderLat(23.847589);
    testData.setProviderLon(-34.1234898);
    
    List<Long> ambulances         = new ArrayList<Long>();
    List<Long> availAmbulances    = new ArrayList<Long>();
    List<Long> assignedAmbulances = new ArrayList<Long>();
    
    ambulances.add(1726387461L);
    ambulances.add(139847598237458L);
    ambulances.add(23459826387465L);
    ambulances.add(968273645345L);
    
    availAmbulances.add(1726387461L);
    availAmbulances.add(968273645345L);
    
    assignedAmbulances.add(23459826387465L);
    
    testData.setAmbulances(ambulances);
    testData.setAvailAmbulances(availAmbulances);
    testData.setAssignedAmbulances(assignedAmbulances);
    
    return testData;
  }

  public static Hospital createTestHospital() {
    Hospital testData = new Hospital();
    
    testData.setHospitalID(1000);
    testData.setHospitalName("Belton Regional Medical Center");
    testData.setHospitalLat(38.8140416);
    testData.setHospitalLon(-94.5043987);
    testData.setAddress("17065 S. 71 Highway Belton, MO 64012");
    String[] levelOfCare = {"basicER", "trauma3"};
    testData.setLevelOfCare(levelOfCare);
    String[] ages = {"child", "teen", "adult"};
    testData.setHostAges(ages);
    testData.setTraumaBeds(2);
    testData.setTraumaBedsFree(2);
    testData.setTraumaBedsOccupied(0);
    testData.setTraumaBedsCleanup(0);
    testData.setErBeds(24);
    testData.setErBedsFree(24);
    testData.setErBedsOccupied(0);
    testData.setErBedsCleanup(0);
    testData.setErDivert("open");
    testData.setTraumaDivert("open");
    testData.setBurnDivert("na");
    testData.setStemiDivert("na");
    testData.setStrokeDivert("na");
    
    return testData;
  }

  public static HospitalDiversionHistory createTestHospitalDiversionHistory() {
    HospitalDiversionHistory testData = new HospitalDiversionHistory();
    
    testData.setDiversionID(876123791765L);
    testData.setHospitalID(196817638726L);
    testData.setCurState("NEBRASKA");
    testData.setDivertCategory("STEMI");
    testData.setChangedOnDate(new Date(777626736867L));
    testData.setChangedByUserID(55125365471652L);
    
    return testData;
  }

  public static User createTestUser() {
    User testData = new User();
    
    testData.setUserID(178236781629837L);
    testData.setUserName("SomeGuy55");
    testData.setUserNameDisplay("Just a Program");
    
    return testData;
  }

  public static UserInformation createTestUserInformation() {
    UserInformation testData = new UserInformation();
    
    testData.setUserID(13451873648752L);
    testData.setPassword("34utaosidhgqp384ht");
    testData.setUserRole(UserRole.AMBULANCE);
    
    List<Long> canDriveList = new ArrayList<Long>();
    canDriveList.add(901273981728935L);
    canDriveList.add(17618976239875L);
    canDriveList.add(12938791763245L);
    canDriveList.add(12983680765871L);
    canDriveList.add(1987087364781235L);
    
    testData.setAuthorizedIDs(canDriveList);

    return testData;
  }
}
