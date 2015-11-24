package com.ac.ems.db.load;

import com.ac.ems.data.EMSProvider;

/**
 * @author ac010168
 *
 */
public class SuperEMSProvider extends EMSProvider {
  
  private int numAmbulances;
  
  public SuperEMSProvider() {
    super();
    numAmbulances = 0;
  }

  /**
   * @return the numAmbulances
   */
  public int getNumAmbulances() {
    return numAmbulances;
  }

  /**
   * @param numAmbulances the numAmbulances to set
   */
  public void setNumAmbulances(int numAmbulances) {
    this.numAmbulances = numAmbulances;
  }
}
