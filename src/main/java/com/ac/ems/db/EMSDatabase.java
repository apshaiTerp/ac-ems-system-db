package com.ac.ems.db;

import java.util.List;

import com.ac.ems.data.Ambulance;
import com.ac.ems.data.AmbulanceTravelHistory;
import com.ac.ems.data.DispatchDetails;
import com.ac.ems.data.DispatchEvent;
import com.ac.ems.data.DispatchEventHistory;
import com.ac.ems.data.DispatchEventLog;
import com.ac.ems.data.EMSProvider;
import com.ac.ems.data.Hospital;
import com.ac.ems.data.HospitalDiversionHistory;
import com.ac.ems.data.User;
import com.ac.ems.data.UserInformation;
import com.ac.ems.data.util.UserComplete;
import com.ac.ems.db.exception.ConfigurationException;
import com.ac.ems.db.exception.DatabaseOperationException;

/**
 * 
 * @author ac010168
 *
 */
public interface EMSDatabase {
  
  public final static String AMBULANCE_TABLE_NAME                  = "ambulance";
  public final static String AMBULANCE_TRAVEL_HISTORY_TABLE_NAME   = "travelhistory";
  public final static String DISPATCH_DETAILS_TABLE_NAME           = "dispatchdetails";
  public final static String DISPATCH_DETAILS_HISTORY_TABLE_NAME   = "dispatchhistory";
  public final static String DISPATCH_EVENT_TABLE_NAME             = "dispatchevent";
  public final static String DISPATCH_EVENT_HISTORY_TABLE_NAME     = "eventhistory";
  public final static String DISPATCH_EVENT_LOG_TABLE_NAME         = "eventlog";
  public final static String EMS_PROVIDER_TABLE_NAME               = "provider";
  public final static String HOSPITAL_TABLE_NAME                   = "hospital";
  public final static String HOSPITAL_DIVERSION_HISTORY_TABLE_NAME = "diversionhistory";
  public final static String USER_TABLE_NAME                       = "user";
  public final static String USER_INFORMATION_TABLE_NAME           = "userinfo";

  //**********  Implementing Basic Maintenance Tasks for the Database Connection  **********
  /**
   * This method should specifically open the connection to the provided database implementation.
   * @throws ConfigurationException Throws this Exception if there are configuration problems that prevent
   * the database connection from being established.
   */
  public void initializeDBConnection() throws ConfigurationException;
  
  /**
   * This method should specifically close the open connection to the provided database implementation.
   * This method should not throw an error if the database connection has not been opened.
   * 
   * @throws ConfigurationException Throws this Exception if there were problems closing down the open
   * database connection.
   */
  public void closeDBConnection() throws ConfigurationException;

  /**
   * This method should insert the new {@link Ambulance} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param ambulance The {@link Ambulance} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link AmbulanceTravelHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param history The {@link AmbulanceTravelHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertAmbulanceTravelHistoryData(AmbulanceTravelHistory history) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchDetails} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param dispatch The {@link DispatchDetails} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchDetailsData(DispatchDetails dispatch) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should archive the now complete {@link DispatchDetails} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param dispatch The {@link DispatchDetails} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchDetailsHistoryData(DispatchDetails dispatch) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEvent} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param event The {@link DispatchEvent} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEventHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param history The {@link DispatchEventHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventHistoryData(DispatchEventHistory history) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEventLog} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param log The {@link DispatchEventLog} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventLogData(DispatchEventLog log) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link EMSProvider} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param provider The {@link EMSProvider} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link Hospital} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param hospital The {@link Hospital} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertHospitalData(Hospital hospital) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link HospitalDiversionHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param diversion The {@link HospitalDiversionHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertHospitalDiversionHistoryData(HospitalDiversionHistory diversion) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link User} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param user The {@link User} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertUserData(User user) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link UserInformation} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param user The {@link UserInformation} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertUserInformationData(UserInformation user) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link Ambulance} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param ambulance The {@link Ambulance} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link DispatchEvent} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param event The {@link DispatchEvent} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link EMSProvider} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param provider The {@link EMSProvider} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link Hospital} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param hospital The {@link Hospital} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateHospitalData(Hospital hospital) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should delete the provided object, flexing for the appropriate object type.  If the object
   * does not exist, this method should not fail.
   * 
   * @param objectToDelete The object to be deleted.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void deleteData(Object objectToDelete) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method is a simple existence check that runs a query based on the provided single row criteria, 
   * and returns true if it found the row, false if not.
   * 
   * @param tableName The table name, as defined by the constants in this interface.
   * @param idName    The ID name, such as 'hospitalID', for the OK value we want to query for
   * @param idValue   The actual value of that ID to find.
   * 
   * @return True if a row exists, false if one does not.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public boolean hasExistingRow(String tableName, String idName, long idValue) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method is a simple generic query based on the provided single row criteria, 
   * the returns the single row, if found, or null.
   * 
   * @param tableName The table name, as defined by the constants in this interface.
   * @param idName    The ID name, such as 'hospitalID', for the OK value we want to query for
   * @param idValue   The actual value of that ID to find.
   * 
   * @return A single Object if found, or null if none found.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public Object querySingleRow(String tableName, String idName, long idValue) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method attempts to identify a user by their user name.
   * 
   * @param userName The user name we want to find.
   * 
   * @return The userID found, or 0 if none found.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public long getUserIDByUserName(String userName) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method gets the User data from the given userID.
   * 
   * @param userID The userID we want information for.
   * 
   * @return An {@link UserComplete} object with the user data, or null if not found
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public UserComplete getUserDataByID(long userID) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will return all EMS Providers that have at least 1 available ambulance.
   * 
   * @return A List of {@link EMSProvider}s, or an empty list if none exist (impossible, but coded for)
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<EMSProvider> getProvidersWithAvailableAmbulances() throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will get all Hospitals that are capable of treating the provided condition.
   * 
   * @param condition The hospital criteria we need to be capable of treating
   * @param excludeIDs The list of hospitals to exclude from the results
   * 
   * @return A List of {@link Hospital}s, or an empty list if none qualify (this is possible).
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<Hospital> getAvailableHospitalsByCondition(String condition, List<Long> excludeIDs) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will generate a list of Hospital names for selection.  The String will be in the format
   * of "<id> - <Hospital Name>".
   * 
   * @return A List of Strings that will represent all Hospitals in the system.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<String> getHospitalNames() throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will generate a list of Provider names for selection.  The String will be in the format
   * of "<id> - <Provider Name>".
   * 
   * @return A List of Strings that will represent all Hospitals in the system.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<String> getProviderNames() throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will fetch the max value of an ID column for a table.
   * 
   * @param tableName The table name, as defined by the constants in this interface.
   * @param idName    The ID name, such as 'hospitalID', for the OK value we want to query for
   * 
   * @return A single ID value, or 1 if no rows exist for this schema.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public long getGenericMaxID(String tableName, String idName) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will fetch the list of all active eventIDs.
   * 
   * @return A List of IDs for all active events.  The List may be empty
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<Long> getDispatchIDsWithEvents() throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will get all active Dispatches.  Pass in true to filter out dispatches that are currently
   * assigned.  
   * 
   * @param excludeEvents True if we want to filter out assigned dispatches, false if we want all active ones.
   * 
   * @return A List of {@link DispatchDetails}.  The List may be empty.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<DispatchDetails> getDispatchDetails(boolean excludeEvents) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will get all active Dispatch Events.
   * 
   * @return A List of {@link DispatchEvent}s.  The List may be empty.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<DispatchEvent> getActiveEvents() throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method will get all active Dispatch Events associated to this hospital.
   * 
   * @param hospitalID the hospital I want events for
   * 
   * @return A List of {@link DispatchEvent}s.  The List may be empty.
   * 
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public List<DispatchEvent> getEventsByTargetHospital(long hospitalID) throws ConfigurationException, DatabaseOperationException;
}
