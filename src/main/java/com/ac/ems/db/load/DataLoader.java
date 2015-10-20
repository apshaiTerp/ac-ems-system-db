package com.ac.ems.db.load;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.ac.ems.data.Ambulance;
import com.ac.ems.data.Hospital;
import com.ac.ems.data.User;
import com.ac.ems.data.UserInformation;
import com.ac.ems.data.enums.UserRole;
import com.ac.ems.data.util.UserComplete;
import com.ac.ems.db.EMSDatabase;
import com.ac.ems.db.exception.ConfigurationException;
import com.ac.ems.db.impl.EMSDatabaseLive;
import com.mongodb.MongoException;

/**
 * @author ac010168
 *
 */
public class DataLoader {

  private String databaseHost;
  private int    databasePort;
  private String databaseName;

  public static void main(String[] args) {
    DataLoader load = new DataLoader();
    
    if (args.length >= 1)
      load.setDatabaseHost(args[0]);
    if (args.length >= 2)
      load.setDatabasePort(Integer.parseInt(args[1]));
    if (args.length >= 3)
      load.setDatabaseName(args[3]);
    
    load.loadData();
  }
  
  public DataLoader() {
    //This represents the default values of the database.  Values will be set to defaults
    //unless overridden by the command line arguments
    databaseHost = "localhost";
    databasePort = 27017;
    databaseName = "emsdb";
  }

  public void loadData() {
    System.out.println ("databaseHost: " + databaseHost);
    System.out.println ("databasePort: " + databasePort);
    System.out.println ("databaseName: " + databaseName);

    try {
      //DO SOME STUFF
      EMSDatabase database = new EMSDatabaseLive(databaseHost, databasePort, databaseName);
      database.initializeDBConnection();
      
      List<UserComplete> allUsers = new LinkedList<UserComplete>();
      
      //Read in the list of Hospitals
      List<Hospital> allHospitals         = HospitalLoader.buildHospitalList();
      
      int userID = 445000;
      
      System.out.println ("There are " + allHospitals.size() + " Hospitals to be loaded");
      
      for (Hospital hospital : allHospitals) {
        //For each hospital, write to database
        database.insertHospitalData(hospital);

        //For each hospital, write 2 users
        List<Long> authorizedIDs = new ArrayList<Long>();
        authorizedIDs.add(hospital.getHospitalID());
        
        userID++;
        User user1            = new User();
        UserInformation info1 = new UserInformation();
        
        user1.setUserID(userID);
        user1.setUserName("hosp" + hospital.getHospitalID() + "-1");
        user1.setUserNameDisplay("Hospital " + hospital.getHospitalID() + " - User 1");
        
        info1.setUserID(userID);
        info1.setPassword(generatePasswordFromUUID());
        info1.setUserRole(UserRole.HOSPITAL);
        info1.setAuthorizedIDs(authorizedIDs);
        
        userID++;
        User user2            = new User();
        UserInformation info2 = new UserInformation();
        
        user2.setUserID(userID);
        user2.setUserName("hosp" + hospital.getHospitalID() + "-2");
        user2.setUserNameDisplay("Hospital " + hospital.getHospitalID() + " - User 2");
        
        info2.setUserID(userID);
        info2.setPassword(generatePasswordFromUUID());
        info2.setUserRole(UserRole.HOSPITAL);
        info2.setAuthorizedIDs(authorizedIDs);
        
        //For later use to write out password data
        allUsers.add(new UserComplete(user1, info1));
        allUsers.add(new UserComplete(user2, info2));
        
        database.insertUserData(user1);
        database.insertUserInformationData(info1);
        database.insertUserData(user2);
        database.insertUserInformationData(info2);
      }
      
      //Read in the list of EMSProviders
      List<SuperEMSProvider> allProviders = EMSProviderLoader.buildEMSProviderList();
      System.out.println ("There are " + allProviders.size() + " EMSProviders to be loaded");
      long ambulanceID = 50000;
      
      for (SuperEMSProvider provider : allProviders) {
        //For each EMSProvider, Generate the list of ambulances
        List<Long> ambulanceIDs      = new ArrayList<Long>(provider.getNumAmbulances());
        List<Long> availAmbulanceIDs = new ArrayList<Long>(provider.getNumAmbulances());
        
        for (int i = 0; i < provider.getNumAmbulances(); i++) {
          ambulanceID++;
          Ambulance ambulance = new Ambulance();
          ambulance.setProviderID(provider.getProviderID());
          ambulance.setAmbulanceID(ambulanceID);
          ambulance.setAmbLat(provider.getProviderLat());
          ambulance.setAmbLon(provider.getProviderLon());
          ambulance.setLastUpdate(new Date());
          
          //Write the ambulance entry
          database.insertAmbulanceData(ambulance);
          
          ambulanceIDs.add(ambulanceID);
          availAmbulanceIDs.add(ambulanceID);
        }
        
        //Add the ambulanceIDs to the provider, then write it.
        provider.setAmbulances(ambulanceIDs);
        provider.setAvailAmbulances(availAmbulanceIDs);
        provider.setAssignedAmbulances(new ArrayList<Long>());
        
        //For each EMSProvider, write to database
        database.insertEMSProviderData(provider);
        
        //For each EMSProvider, write 2 users
        List<Long> authorizedIDs = new ArrayList<Long>();
        authorizedIDs.add(provider.getProviderID());
        
        userID++;
        User user1            = new User();
        UserInformation info1 = new UserInformation();
        
        user1.setUserID(userID);
        user1.setUserName("ems" + (provider.getProviderID() % 10000) + "-1");
        user1.setUserNameDisplay("EMS Provider " + provider.getProviderID() + " - User 1");
        
        info1.setUserID(userID);
        info1.setPassword(generatePasswordFromUUID());
        info1.setUserRole(UserRole.EMS);
        info1.setAuthorizedIDs(authorizedIDs);
        
        userID++;
        User user2            = new User();
        UserInformation info2 = new UserInformation();
        
        user2.setUserID(userID);
        user2.setUserName("ems" + (provider.getProviderID() % 10000) + "-2");
        user2.setUserNameDisplay("EMS Provider " + provider.getProviderID() + " - User 2");
        
        info2.setUserID(userID);
        info2.setPassword(generatePasswordFromUUID());
        info2.setUserRole(UserRole.EMS);
        info2.setAuthorizedIDs(authorizedIDs);
        
        //For later use to write out password data
        allUsers.add(new UserComplete(user1, info1));
        allUsers.add(new UserComplete(user2, info2));
        
        database.insertUserData(user1);
        database.insertUserInformationData(info1);
        database.insertUserData(user2);
        database.insertUserInformationData(info2);

        //For each EMSProvider, create #Ambulances +2 Users.
        //For each created user, associate them to all ambulances for the provider
        for (int userLoop = 0; userLoop < (ambulanceIDs.size() + 2); userLoop++) {
          userID++;
          User ambUser            = new User();
          UserInformation ambInfo = new UserInformation();
          
          ambUser.setUserID(userID);
          ambUser.setUserName("amb" + (provider.getProviderID() % 10000) + "-" + (userLoop + 1));
          ambUser.setUserNameDisplay("EMS Provider " + provider.getProviderID() + " - Medic " + (userLoop + 1));
          
          ambInfo.setUserID(userID);
          ambInfo.setPassword(generatePasswordNumeric());
          ambInfo.setUserRole(UserRole.AMBULANCE);
          ambInfo.setAuthorizedIDs(ambulanceIDs);
          
          allUsers.add(new UserComplete(ambUser, ambInfo));
          
          database.insertUserData(ambUser);
          database.insertUserInformationData(ambInfo);
        }
      }
      
      //Create 2 Dispatch Users
      userID++;
      User dispatchUser1            = new User();
      UserInformation dispatchInfo1 = new UserInformation();
      
      dispatchUser1.setUserID(userID);
      dispatchUser1.setUserName("dispatch-1");
      dispatchUser1.setUserNameDisplay("EMS Dispatcher 1");
      
      dispatchInfo1.setUserID(userID);
      dispatchInfo1.setPassword(generatePasswordFromUUID());
      dispatchInfo1.setUserRole(UserRole.DISPATCH);
      dispatchInfo1.setAuthorizedIDs(new ArrayList<Long>());

      userID++;
      User dispatchUser2            = new User();
      UserInformation dispatchInfo2 = new UserInformation();
      
      dispatchUser2.setUserID(userID);
      dispatchUser2.setUserName("dispatch-2");
      dispatchUser2.setUserNameDisplay("EMS Dispatcher 2");
      
      dispatchInfo2.setUserID(userID);
      dispatchInfo2.setPassword(generatePasswordFromUUID());
      dispatchInfo2.setUserRole(UserRole.DISPATCH);
      dispatchInfo2.setAuthorizedIDs(new ArrayList<Long>());

      allUsers.add(new UserComplete(dispatchUser1, dispatchInfo1));
      allUsers.add(new UserComplete(dispatchUser2, dispatchInfo2));
      
      database.insertUserData(dispatchUser1);
      database.insertUserInformationData(dispatchInfo1);
      database.insertUserData(dispatchUser2);
      database.insertUserInformationData(dispatchInfo2);

      //Create 2 SUPERUSERS
      userID++;
      User superUser1            = new User();
      UserInformation superInfo1 = new UserInformation();
      
      superUser1.setUserID(userID);
      superUser1.setUserName("superuser-1");
      superUser1.setUserNameDisplay("Super User 1");
      
      superInfo1.setUserID(userID);
      superInfo1.setPassword(generatePasswordFromUUID());
      superInfo1.setUserRole(UserRole.SUPER);
      superInfo1.setAuthorizedIDs(new ArrayList<Long>());

      userID++;
      User superUser2            = new User();
      UserInformation superInfo2 = new UserInformation();
      
      superUser2.setUserID(userID);
      superUser2.setUserName("superuser-2");
      superUser2.setUserNameDisplay("Super User 2");
      
      superInfo2.setUserID(userID);
      superInfo2.setPassword(generatePasswordFromUUID());
      superInfo2.setUserRole(UserRole.SUPER);
      superInfo2.setAuthorizedIDs(new ArrayList<Long>());

      allUsers.add(new UserComplete(superUser1, superInfo1));
      allUsers.add(new UserComplete(superUser2, superInfo2));
      
      database.insertUserData(superUser1);
      database.insertUserInformationData(superInfo1);
      database.insertUserData(superUser2);
      database.insertUserInformationData(superInfo2);
      
      //Write User Index Directory
      for (UserComplete user : allUsers) {
        System.out.println ("Role: " + user.getUserInformation().getUserRole() + "\t" + 
            "UserName: " + user.getUser().getUserName() + "\t" + 
            "UserID: " + user.getUser().getUserID() + "\t" + 
            "Password: " + user.getUserInformation().getPassword() + "\t" + 
            "AuthIDs " + generateIDList(user.getUserInformation().getAuthorizedIDs()));
      }
      
      database.closeDBConnection();
    } catch (ConfigurationException ce) {
      System.out.println ("Your Mongo Connection may be misconfigured: " + ce.getMessage());
      ce.printStackTrace();
    } catch (MongoException me) {
      System.out.println ("Your Mongo query/insert looks wrong: " + me.getMessage());
      me.printStackTrace();
    } catch (Throwable t) {
      System.out.println ("Something bad happened here: " + t.getMessage());
      t.printStackTrace();
    }

  }
  
  /**
   * @return the databaseHost
   */
  public String getDatabaseHost() {
    return databaseHost;
  }

  /**
   * @param databaseHost the databaseHost to set
   */
  public void setDatabaseHost(String databaseHost) {
    this.databaseHost = databaseHost;
  }

  /**
   * @return the databasePort
   */
  public int getDatabasePort() {
    return databasePort;
  }

  /**
   * @param databasePort the databasePort to set
   */
  public void setDatabasePort(int databasePort) {
    this.databasePort = databasePort;
  }

  /**
   * @return the databaseName
   */
  public String getDatabaseName() {
    return databaseName;
  }

  /**
   * @param databaseName the databaseName to set
   */
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }
  
  /**
   * UUID are in the format of 067e6162-3b6f-4ae2-a171-2470b63dff00, or put generically:
   * xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx.
   * 
   * Our passwords will be the first three blocks with the hyphens removed.
   * 
   * @return A password of 8 characters with no hyphens
   */
  private String generatePasswordFromUUID() {
    UUID idValue = UUID.randomUUID();
    
    String password = idValue.toString();
    return password.replace("-", "").substring(0, 8);
  }
  
  private String generatePasswordNumeric() {
    Random random = new Random();
    int number = random.nextInt(100000000);
    
    String password = "" + number;
    while (password.length() < 8)
      password = "0" + password;
    
    return password;
  }
  
  private String generateIDList(List<Long> authIDs) {
    if (authIDs == null) return "[]";
    if (authIDs.size() == 0) return "[]";
    String result = "[" + authIDs.get(0);
    for (int pos = 1; pos < authIDs.size(); pos++)
      result+= "," + authIDs.get(pos);
    result += "]";
    return result;
  }
}
