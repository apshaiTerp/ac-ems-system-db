package com.ac.ems.db.impl;

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
import com.ac.ems.db.EMSDatabase;
import com.ac.ems.db.exception.ConfigurationException;
import com.ac.ems.db.exception.DatabaseOperationException;
import com.ac.ems.db.mongo.AmbulanceConverter;
import com.ac.ems.db.mongo.AmbulanceTravelHistoryConverter;
import com.ac.ems.db.mongo.DispatchDetailsConverter;
import com.ac.ems.db.mongo.DispatchEventConverter;
import com.ac.ems.db.mongo.DispatchEventHistoryConverter;
import com.ac.ems.db.mongo.DispatchEventLogConverter;
import com.ac.ems.db.mongo.EMSProviderConverter;
import com.ac.ems.db.mongo.HospitalConverter;
import com.ac.ems.db.mongo.HospitalDiversionHistoryConverter;
import com.ac.ems.db.mongo.UserConverter;
import com.ac.ems.db.mongo.UserInformationConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * @author ac010168
 *
 */
public class EMSDatabaseLive implements EMSDatabase {

  /** Reference to the host address for mongo.  Might just be "localhost". */
  private final String mongoHostAddress;
  /** Port number used for mongo.  Is typically 27017. */
  private final int mongoPort;
  /** Database name to be connected to. */
  private final String databaseName;
  
  /** Reference to the active mongoClient. */
  private MongoClient mongoClient;
  /** Reference to the active database used by this connection. */
  private DB mongoDB;
  
  /** Global setting to help manage debug println statements */
  public static boolean debugMode = false;
  
  public EMSDatabaseLive(String mongoHostAddress, int mongoPort, String databaseName) {
    this.mongoHostAddress = mongoHostAddress;
    this.mongoPort        = mongoPort;
    this.databaseName     = databaseName;
    
    mongoClient = null;
    mongoDB     = null;
  }

  /**
   * @return the mongoHostAddress
   */
  public String getMongoHostAddress() {
    return mongoHostAddress;
  }

  /**
   * @return the mongoPort
   */
  public int getMongoPort() {
    return mongoPort;
  }

  /**
   * @return the databaseName
   */
  public String getDatabaseName() {
    return databaseName;
  }

  /**
   * @return the mongoClient
   */
  public MongoClient getMongoClient() {
    return mongoClient;
  }

  /**
   * @param mongoClient the mongoClient to set
   */
  public void setMongoClient(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  /**
   * @return the mongoDB
   */
  public DB getMongoDB() {
    return mongoDB;
  }

  /**
   * @param mongoDB the mongoDB to set
   */
  public void setMongoDB(DB mongoDB) {
    this.mongoDB = mongoDB;
  }

  /*
   * (non-Javadoc)
   * @see com.ac.games.db.GamesDatabase#initializeDBConnection()
   */
  public void initializeDBConnection() throws ConfigurationException {
    //Checking to see if connection is already open...
    if (mongoClient != null) {
      System.out.println ("The connection is already open, do not reset.");
      return;
    }
    
    //Initializing Database Connection Client
    try {
      mongoClient = new MongoClient(mongoHostAddress, mongoPort);
      mongoClient.setWriteConcern(WriteConcern.JOURNALED);
    } catch (Throwable t) {
      mongoClient = null;
      throw new ConfigurationException("Unable to connect to MongoDB at " + mongoHostAddress + ":" + mongoPort);      
    }

    try {
      mongoDB = mongoClient.getDB(databaseName);
    } catch (Throwable t) {
      try { mongoClient.close(); } catch (Throwable t2) { /** Ignore Errors */ }
      mongoClient = null;
      mongoDB     = null;
      throw new ConfigurationException("Unable to connect to Mongo Database [" + databaseName + "]");
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.games.db.GamesDatabase#closeDBConnection()
   */
  public void closeDBConnection() throws ConfigurationException {
    //Close the current collection
    try {
      if (mongoClient != null)
        mongoClient.close();
    } catch (Throwable t) {
      t.printStackTrace();
    }
    
    mongoClient = null;
    mongoDB     = null;
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertAmbulanceData(com.ac.ems.data.Ambulance)
   */
  public void insertAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (ambulance == null)
      throw new DatabaseOperationException("The provided ambulance object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Because we are using non-Mongo based primary keys, we need to specifically check first to see if the object
      //exists, and if it does, we need to do an update instead
      if (hasExistingRow(AMBULANCE_TABLE_NAME, "ambulanceID", ambulance.getAmbulanceID())) {
        if (debugMode)
          System.out.println ("Converting insert into update because of prior document");
        updateAmbulanceData(ambulance);
        return;
      }
      
      //Open the collection, i.e. table
      DBCollection ambulanceCollection = mongoDB.getCollection(AMBULANCE_TABLE_NAME);
      
      BasicDBObject addObject = AmbulanceConverter.convertAmbulanceToMongo(ambulance);
      WriteResult result = ambulanceCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertAmbulanceTravelHistoryData(com.ac.ems.data.AmbulanceTravelHistory)
   */
  public void insertAmbulanceTravelHistoryData(AmbulanceTravelHistory history) throws ConfigurationException,
      DatabaseOperationException {
    //Check basic pre-conditions
    if (history == null)
      throw new DatabaseOperationException("The provided history object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection historyCollection = mongoDB.getCollection(AMBULANCE_TRAVEL_HISTORY_TABLE_NAME);
      
      BasicDBObject addObject = AmbulanceTravelHistoryConverter.convertAmbulanceTravelHistoryToMongo(history);
      WriteResult result = historyCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertDispatchDetailsData(com.ac.ems.data.DispatchDetails)
   */
  public void insertDispatchDetailsData(DispatchDetails dispatch) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (dispatch == null)
      throw new DatabaseOperationException("The provided dispatch object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection dispatchCollection = mongoDB.getCollection(DISPATCH_DETAILS_TABLE_NAME);
      
      BasicDBObject addObject = DispatchDetailsConverter.convertDispatchDetailsToMongo(dispatch);
      WriteResult result = dispatchCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertDispatchEventData(com.ac.ems.data.DispatchEvent)
   */
  public void insertDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (event == null)
      throw new DatabaseOperationException("The provided event object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Because we are using non-Mongo based primary keys, we need to specifically check first to see if the object
      //exists, and if it does, we need to do an update instead
      if (hasExistingRow(DISPATCH_EVENT_TABLE_NAME, "eventID", event.getEventID())) {
        if (debugMode)
          System.out.println ("Converting insert into update because of prior document");
        updateDispatchEventData(event);
        return;
      }
      
      //Open the collection, i.e. table
      DBCollection eventCollection = mongoDB.getCollection(DISPATCH_EVENT_TABLE_NAME);
      
      BasicDBObject addObject = DispatchEventConverter.convertDispatchEventToMongo(event);
      WriteResult result = eventCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertDispatchEventHistoryData(com.ac.ems.data.DispatchEventHistory)
   */
  public void insertDispatchEventHistoryData(DispatchEventHistory history) throws ConfigurationException,
      DatabaseOperationException {
    //Check basic pre-conditions
    if (history == null)
      throw new DatabaseOperationException("The provided history object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection historyCollection = mongoDB.getCollection(DISPATCH_EVENT_HISTORY_TABLE_NAME);
      
      BasicDBObject addObject = DispatchEventHistoryConverter.convertDispatchEventHistoryToMongo(history);
      WriteResult result = historyCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertDispatchEventLogData(com.ac.ems.data.DispatchEventLog)
   */
  public void insertDispatchEventLogData(DispatchEventLog log) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (log == null)
      throw new DatabaseOperationException("The provided log object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection logCollection = mongoDB.getCollection(DISPATCH_EVENT_LOG_TABLE_NAME);
      
      BasicDBObject addObject = DispatchEventLogConverter.convertDispatchEventLogToMongo(log);
      WriteResult result = logCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertEMSProviderData(com.ac.ems.data.EMSProvider)
   */
  public void insertEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (provider == null)
      throw new DatabaseOperationException("The provided provider object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Because we are using non-Mongo based primary keys, we need to specifically check first to see if the object
      //exists, and if it does, we need to do an update instead
      if (hasExistingRow(EMS_PROVIDER_TABLE_NAME, "providerID", provider.getProviderID())) {
        if (debugMode)
          System.out.println ("Converting insert into update because of prior document");
        updateEMSProviderData(provider);
        return;
      }
      
      //Open the collection, i.e. table
      DBCollection providerCollection = mongoDB.getCollection(EMS_PROVIDER_TABLE_NAME);
      
      BasicDBObject addObject = EMSProviderConverter.convertEMSProviderToMongo(provider);
      WriteResult result = providerCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertHospitalData(com.ac.ems.data.Hospital)
   */
  public void insertHospitalData(Hospital hospital) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (hospital == null)
      throw new DatabaseOperationException("The provided hospital object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Because we are using non-Mongo based primary keys, we need to specifically check first to see if the object
      //exists, and if it does, we need to do an update instead
      if (hasExistingRow(HOSPITAL_TABLE_NAME, "hospitalID", hospital.getHospitalID())) {
        if (debugMode)
          System.out.println ("Converting insert into update because of prior document");
        updateHospitalData(hospital);
        return;
      }
      
      //Open the collection, i.e. table
      DBCollection hospitalCollection = mongoDB.getCollection(HOSPITAL_TABLE_NAME);
      
      BasicDBObject addObject = HospitalConverter.convertHospitalToMongo(hospital);
      WriteResult result = hospitalCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertHospitalDiversionHistoryData(com.ac.ems.data.HospitalDiversionHistory)
   */
  public void insertHospitalDiversionHistoryData(HospitalDiversionHistory diversion) throws ConfigurationException,
      DatabaseOperationException {
    //Check basic pre-conditions
    if (diversion == null)
      throw new DatabaseOperationException("The provided diversion object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection diversionCollection = mongoDB.getCollection(HOSPITAL_DIVERSION_HISTORY_TABLE_NAME);
      
      BasicDBObject addObject = HospitalDiversionHistoryConverter.convertHospitalDiversionHistoryToMongo(diversion);
      WriteResult result = diversionCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertUserData(com.ac.ems.data.User)
   */
  public void insertUserData(User user) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (user == null)
      throw new DatabaseOperationException("The provided user object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection userCollection = mongoDB.getCollection(USER_TABLE_NAME);
      
      BasicDBObject addObject = UserConverter.convertUserToMongo(user);
      WriteResult result = userCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#insertUserInformationData(com.ac.ems.data.UserInformation)
   */
  public void insertUserInformationData(UserInformation user) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (user == null)
      throw new DatabaseOperationException("The provided user object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection userCollection = mongoDB.getCollection(USER_INFORMATION_TABLE_NAME);
      
      BasicDBObject addObject = UserInformationConverter.convertUserInformationToMongo(user);
      WriteResult result = userCollection.insert(addObject);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this insert converted to an upsert?             " + result.isUpdateOfExisting());
        System.out.println ("The new document _id value added:                   " + addObject.get("_id"));
      }
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this insert: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the insert", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#updateAmbulanceData(com.ac.ems.data.Ambulance)
   */
  public void updateAmbulanceData(Ambulance ambulance) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (ambulance == null)
      throw new DatabaseOperationException("The provided ambulance object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection ambulanceCollection = mongoDB.getCollection(AMBULANCE_TABLE_NAME);
      BasicDBObject queryObject  = AmbulanceConverter.convertIDTOQuery(ambulance);
      BasicDBObject updateObject = AmbulanceConverter.convertAmbulanceToMongo(ambulance);
      WriteResult result = ambulanceCollection.update(queryObject, updateObject, true, false);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this update converted to an insert?             " + !result.isUpdateOfExisting());
      }
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this update: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the update", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#updateDispatchEventData(com.ac.ems.data.DispatchEvent)
   */
  public void updateDispatchEventData(DispatchEvent event) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (event == null)
      throw new DatabaseOperationException("The provided event object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection eventCollection = mongoDB.getCollection(DISPATCH_EVENT_TABLE_NAME);
      BasicDBObject queryObject  = DispatchEventConverter.convertIDToQuery(event);
      BasicDBObject updateObject = DispatchEventConverter.convertDispatchEventToMongo(event);
      WriteResult result = eventCollection.update(queryObject, updateObject, true, false);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this update converted to an insert?             " + !result.isUpdateOfExisting());
      }
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this update: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the update", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#updateEMSProviderData(com.ac.ems.data.EMSProvider)
   */
  public void updateEMSProviderData(EMSProvider provider) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (provider == null)
      throw new DatabaseOperationException("The provided provider object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection providerCollection = mongoDB.getCollection(EMS_PROVIDER_TABLE_NAME);
      BasicDBObject queryObject  = EMSProviderConverter.convertIDToQuery(provider);
      BasicDBObject updateObject = EMSProviderConverter.convertEMSProviderToMongo(provider);
      WriteResult result = providerCollection.update(queryObject, updateObject, true, false);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this update converted to an insert?             " + !result.isUpdateOfExisting());
      }
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this update: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the update", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#updateHospitalData(com.ac.ems.data.Hospital)
   */
  public void updateHospitalData(Hospital hospital) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (hospital == null)
      throw new DatabaseOperationException("The provided hospital object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
    
    //Run the operation
    try {
      //Open the collection, i.e. table
      DBCollection hospitalCollection = mongoDB.getCollection(HOSPITAL_TABLE_NAME);
      BasicDBObject queryObject  = HospitalConverter.convertIDTOQuery(hospital);
      BasicDBObject updateObject = HospitalConverter.convertHospitalToMongo(hospital);
      WriteResult result = hospitalCollection.update(queryObject, updateObject, true, false);
      
      if (debugMode) {
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
        System.out.println ("Was this update converted to an insert?             " + !result.isUpdateOfExisting());
      }
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this update: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the update", t);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.ac.ems.db.EMSDatabase#deleteData(java.lang.Object)
   */
  public void deleteData(Object objectToDelete) throws ConfigurationException, DatabaseOperationException {
    //Check basic pre-conditions
    if (objectToDelete == null)
      throw new DatabaseOperationException("The provided objectToDelete object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");
      
    try { 
      DBCollection collection = null;
      BasicDBObject deleteObject = null;
      
      if (objectToDelete instanceof Ambulance) {
        collection = mongoDB.getCollection(AMBULANCE_TABLE_NAME);
        deleteObject = AmbulanceConverter.convertIDTOQuery((Ambulance)objectToDelete);
      } else if (objectToDelete instanceof AmbulanceTravelHistory) {
        collection = mongoDB.getCollection(AMBULANCE_TRAVEL_HISTORY_TABLE_NAME);
        deleteObject = AmbulanceTravelHistoryConverter.convertIDToQuery((AmbulanceTravelHistory)objectToDelete);
      } else if (objectToDelete instanceof DispatchDetails) {
        collection = mongoDB.getCollection(DISPATCH_DETAILS_TABLE_NAME);
        deleteObject = DispatchDetailsConverter.convertIDToQuery((DispatchDetails)objectToDelete);
      } else if (objectToDelete instanceof DispatchEvent) {
        collection = mongoDB.getCollection(DISPATCH_EVENT_TABLE_NAME);
        deleteObject = DispatchEventConverter.convertIDToQuery((DispatchEvent)objectToDelete);
      } else if (objectToDelete instanceof DispatchEventHistory) {
        collection = mongoDB.getCollection(DISPATCH_EVENT_HISTORY_TABLE_NAME);
        deleteObject = DispatchEventHistoryConverter.convertIDToQuery((DispatchEventHistory)objectToDelete);
      } else if (objectToDelete instanceof DispatchEventLog) {
        collection = mongoDB.getCollection(DISPATCH_EVENT_LOG_TABLE_NAME);
        deleteObject = DispatchEventLogConverter.convertIDToQuery((DispatchEventLog)objectToDelete);
      } else if (objectToDelete instanceof EMSProvider) {
        collection = mongoDB.getCollection(EMS_PROVIDER_TABLE_NAME);
        deleteObject = EMSProviderConverter.convertIDToQuery((EMSProvider)objectToDelete);
      } else if (objectToDelete instanceof Hospital) {
        collection = mongoDB.getCollection(HOSPITAL_TABLE_NAME);
        deleteObject = HospitalConverter.convertIDTOQuery((Hospital)objectToDelete);
      } else if (objectToDelete instanceof HospitalDiversionHistory) {
        collection = mongoDB.getCollection(HOSPITAL_DIVERSION_HISTORY_TABLE_NAME);
        deleteObject = HospitalDiversionHistoryConverter.convertIDToQuery((HospitalDiversionHistory)objectToDelete);
      } else if (objectToDelete instanceof User) {
        collection = mongoDB.getCollection(USER_TABLE_NAME);
        deleteObject = UserConverter.convertIDToQuery((User)objectToDelete);
      } else if (objectToDelete instanceof UserInformation) {
        collection = mongoDB.getCollection(USER_INFORMATION_TABLE_NAME);
        deleteObject = UserInformationConverter.convertIDToQuery((UserInformation)objectToDelete);
      }
      
      WriteResult result = collection.remove(deleteObject);
      if (debugMode)
        System.out.println ("The number of documents impacted by this operation: " + result.getN());
      
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this delete: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the delete", t);
    }
  }

  public boolean hasExistingRow(String tableName, String idName, long idValue) throws ConfigurationException,
      DatabaseOperationException {
    //Check basic pre-conditions
    if (tableName == null)
      throw new DatabaseOperationException("The provided tableName object was null.");
    if (idName == null)
      throw new DatabaseOperationException("The provided idName object was null.");
    if (idValue <= 0)
      throw new DatabaseOperationException("The provided idValue object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");

    try {
      DBCollection collection   = mongoDB.getCollection(tableName);
      BasicDBObject queryObject = new BasicDBObject(idName, idValue);
      
      boolean isFound = false;
      DBCursor cursor = collection.find(queryObject);
      while (cursor.hasNext()) {
        isFound = true;
      }
      try { cursor.close(); } catch (Throwable t) { /** Ignore Errors */ }
      return isFound;
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this query: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the query", t);
    }
  }

  public Object querySingleRow(String tableName, String idName, long idValue) throws ConfigurationException,
      DatabaseOperationException {
    //Check basic pre-conditions
    if (tableName == null)
      throw new DatabaseOperationException("The provided tableName object was null.");
    if (idName == null)
      throw new DatabaseOperationException("The provided idName object was null.");
    if (idValue <= 0)
      throw new DatabaseOperationException("The provided idValue object was null.");
    
    if (mongoClient == null || mongoDB == null)
      throw new ConfigurationException("There is a problem with the database connection.");

    try {
      DBCollection collection   = mongoDB.getCollection(tableName);
      BasicDBObject queryObject = new BasicDBObject(idName, idValue);
      
      Object result = null;
      DBCursor cursor = collection.find(queryObject);
      while (cursor.hasNext()) {
        if (result != null)
          throw new DatabaseOperationException("I qualified multiple rows in the single row query");
        
        DBObject object = cursor.next();
        if (tableName.equalsIgnoreCase(AMBULANCE_TABLE_NAME))
          result = AmbulanceConverter.convertMongoToAmbulance(object);
        else if (tableName.equalsIgnoreCase(AMBULANCE_TRAVEL_HISTORY_TABLE_NAME))
          result = AmbulanceTravelHistoryConverter.convertMongoToAmbulanceTravelHistory(object);
        else if (tableName.equalsIgnoreCase(DISPATCH_DETAILS_TABLE_NAME))
          result = DispatchDetailsConverter.convertMongoToDispatchDetails(object);
        else if (tableName.equalsIgnoreCase(DISPATCH_EVENT_TABLE_NAME))
          result = DispatchEventConverter.convertMongoToDispatchEvent(object);
        else if (tableName.equalsIgnoreCase(DISPATCH_EVENT_HISTORY_TABLE_NAME))
          result = DispatchEventHistoryConverter.convertMongoToDispatchEventHistory(object);
        else if (tableName.equalsIgnoreCase(DISPATCH_EVENT_LOG_TABLE_NAME))
          result = DispatchEventLogConverter.convertMongoToDispatchEventLog(object);
        else if (tableName.equalsIgnoreCase(EMS_PROVIDER_TABLE_NAME))
          result = EMSProviderConverter.convertMongoToEMSProvider(object);
        else if (tableName.equalsIgnoreCase(HOSPITAL_TABLE_NAME))
          result = HospitalConverter.convertMongoToHospital(object);
        else if (tableName.equalsIgnoreCase(HOSPITAL_DIVERSION_HISTORY_TABLE_NAME))
          result = HospitalDiversionHistoryConverter.convertMongoToHospitalDiversionHistory(object);
        else if (tableName.equalsIgnoreCase(USER_TABLE_NAME))
          result = UserConverter.convertMongoToUser(object);
        else if (tableName.equalsIgnoreCase(USER_INFORMATION_TABLE_NAME))
          result = UserInformationConverter.convertMongoToUserInformation(object);
        else throw new DatabaseOperationException("The provided tableName '" + tableName + "' is not a known table.");
      }
      try { cursor.close(); } catch (Throwable t) { /** Ignore Errors */ }
      return result;
    } catch (MongoException me) {
      throw new DatabaseOperationException("Mongo raised an exception to this query: " + me.getMessage(), me);
    } catch (Throwable t) {
      throw new DatabaseOperationException("Something bad happened executing the query", t);
    }
  }
}
