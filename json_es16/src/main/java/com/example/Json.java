package com.example;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

public class Json {
    public void parse(String inputFile, String array, List<String> nameObject, List<String> subObject) throws IOException {
        System.out.println(inputFile);
        JSONObject obj = new JSONObject(inputFile);
        if (obj != null) {
            org.json.JSONArray dataset = obj.optJSONArray(array);
            if (dataset != null) {
                for (int i = 0; i < dataset.length(); i++) {
                    JSONObject current = dataset.optJSONObject(i);

                    for(int j = 0; j < nameObject.size(); j++){
                        if (subObject != null && subObject.get(0) == nameObject.get(j)) {
                            JSONObject subObjectJSON = current.optJSONObject(nameObject.get(j));
                            for(int k = 1; k < subObject.size(); k++){
                                if(subObjectJSON.get(subObject.get(k)) != null)
                                    System.out.println(subObjectJSON.get(subObject.get(k)).toString());
                            }
                        } else if (nameObject.get(j)!= null){
                            System.out.println(current.get(nameObject.get(j)).toString());
                        }
                    }
                }
            }
        }
    }

    /*private String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = path.getBytes();
        return new String(encoded, encoding);
    }*/


    public String createJson(List<String> key, List<String> dati) {
        String ris = "{";
        for (int i = 0; i < dati.size(); i++) {
            if(i != 0){
                ris += ", ";
            }
            ris += "\"" + key.get(i) + "\": \"" + dati.get(i) + "\"";

        }
        ris += "}";
        return ris;
    }
}
