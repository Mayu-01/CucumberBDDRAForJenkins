package org.example;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GlobalVariables {

    public static Map<String, Object> detailsMap;
    public static Map<String, String> newMap;
    public static Map<String,String> globalDataMap=new HashMap<>();

               public static void fetchServiceDetails(String service){
               try {
                   Yaml yaml = new Yaml();
                   InputStream fis=new FileInputStream(new File("servicedetails/qa1_serviceDetails.yaml"));
                   detailsMap = yaml.load(fis);
                    newMap = (Map<String,String>) detailsMap.get(service);
                   System.out.println("New Map" + newMap);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
           }

//           public static void fetchGlobaldata(){
//               globalDataMap=new HashMap<>();
//           }

}
