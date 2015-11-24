package com.ac.ems.db.load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ac010168
 *
 */
public class EMSProviderLoader {

  public static List<SuperEMSProvider> buildEMSProviderList() throws IOException {
    List<SuperEMSProvider> emsProviders = new LinkedList<SuperEMSProvider>();
    
    //BufferedReader reader = new BufferedReader(new FileReader("/Users/ac010168/Desktop/emsList2.txt"));
    BufferedReader reader = new BufferedReader(new FileReader("/home/adam/umkc/ac-ems-system-db/src/data/emsList.txt"));

    //Skip the first line
    reader.readLine();
    String line = reader.readLine();
    while (line != null) {
      SuperEMSProvider provider = new SuperEMSProvider();
      StringTokenizer tokenizer = new StringTokenizer(line, "\t");
      
      provider.setProviderName(tokenizer.nextToken());
      provider.setProviderID(Long.parseLong(tokenizer.nextToken()));
      provider.setProviderAddress(tokenizer.nextToken());
      provider.setProviderLat(Double.parseDouble(tokenizer.nextToken()));
      provider.setProviderLon(Double.parseDouble(tokenizer.nextToken()));
      provider.setNumAmbulances(Integer.parseInt(tokenizer.nextToken()));
      emsProviders.add(provider);
      
      //DEBUG
      System.out.println ("EMSProvider:       " + provider.getProviderName());
      System.out.println ("  ProviderID:      " + provider.getProviderID());
      System.out.println ("  ProviderAddress: " + provider.getProviderAddress());
      System.out.println ("  Provider Coords: (" + provider.getProviderLat() + "," + provider.getProviderLon() + ")");
      System.out.println ("  Num Ambulances:  " + provider.getNumAmbulances());
      
      line = reader.readLine();
    }
    
    reader.close();
    return emsProviders;
  }
}
