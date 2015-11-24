package com.ac.ems.db;

import com.ac.ems.db.impl.EMSDatabaseLive;

/**
 * Factory pattern for initializing the Mongo connection for our database.
 * 
 * @author ac010168
 *
 */
public class MongoDBFactory {
  
  /** This is the static reference to access the Mongo Database class as a singleton. */
  private static EMSDatabase database = null;

  /**
   * Factory Creation method to generate a new MongoDB Database connection.
   * 
   * @param mongoHostAddress The hostAddress where the mongoDB server is running
   * @param mongoPort The port to connect to MongoDB
   * @param databaseName The database name we want to work with.
   *
   * @return A new {@link EMSDatabaseLive} object.
   */
  public final static EMSDatabase createMongoDatabase(String mongoHostAddress, int mongoPort, String databaseName) {
    if (database == null) 
      database = new EMSDatabaseLive(mongoHostAddress, mongoPort, databaseName);
    return database;
  }
  
  /**
   * This method is helpful when the database connection has already been opened.
   * 
   * @return the {@link EMSDatabase} reference, or null if it hasn't been created
   */
  public final static EMSDatabase getMongoDatabase() {
    return database;
  }
}
