package com.ac.ems.db;

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
import com.ac.ems.db.exception.ConfigurationException;
import com.ac.ems.db.exception.DatabaseOperationException;

/**
 * @author ac010168
 *
 */
public interface EMSDatabase {
  
  public final static String AMBULANCE_TABLE_NAME                  = "ambulance";
  public final static String AMBULANCE_TRAVEL_HISTORY_TABLE_NAME   = "travelhistory";
  public final static String DISPATCH_DETAILS_TABLE_NAME           = "dispatchdetails";
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
   * @param game The {@link Ambulance} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link AmbulanceTravelHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link AmbulanceTravelHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertAmbulanceTravelHistoryData(AmbulanceTravelHistory history) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchDetails} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link DispatchDetails} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchDetailsData(DispatchDetails dispatch) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEvent} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link DispatchEvent} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEventHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link DispatchEventHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventHistoryData(DispatchEventHistory history) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link DispatchEventLog} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link DispatchEventLog} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertDispatchEventLogData(DispatchEventLog log) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link EMSProvider} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link EMSProvider} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link Hospital} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link Hospital} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertHospitalData(Hospital hospital) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link HospitalDiversionHistory} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link HospitalDiversionHistory} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertHospitalDiversionHistoryData(HospitalDiversionHistory diversion) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link User} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link User} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertUserData(User user) throws ConfigurationException, DatabaseOperationException;

  /**
   * This method should insert the new {@link UserInformation} data into the database.  If the object already
   * exists, this method should throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link UserInformation} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void insertUserInformationData(UserInformation user) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link Ambulance} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link Ambulance} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link DispatchEvent} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link DispatchEvent} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link EMSProvider} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link EMSProvider} object to be written to the database.
   * @throws ConfigurationException Throws this exception if the database connection is not active.
   * @throws DatabaseOperationException Throws this exception if there are errors during the execution
   * of the requested operation.
   */
  public void updateEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException;
  
  /**
   * This method should update the existing {@link Hospital} data into the database.  If the object does
   * not exist (or no rows were updated), this method may throw a {@link DatabaseOperationException}.
   * 
   * @param game The {@link Hospital} object to be written to the database.
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
}
