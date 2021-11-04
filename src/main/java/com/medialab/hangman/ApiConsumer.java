package com.medialab.hangman;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.medialab.hangman.Exceptions.ApiExceptions.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class ApiConsumer {

    static String description;

    public static void fetchData(String open_library_id) throws ApiException, UnirestException {
        String endpoint = "https://openlibrary.org/books/" + open_library_id + ".json";
     
        try {
            HttpResponse<JsonNode> response = Unirest.get(endpoint).asJson();
            String json_str = response.getBody().toString();
            JsonObject json_resp = JsonParser.parseString(json_str).getAsJsonObject();
            if (!json_resp.has("error")) {

                JsonObject json_desc = json_resp.get("description").getAsJsonObject();
                description = json_desc.get("value").getAsString();
                return;
            }

        } catch (UnirestException ue) {
            ue.printStackTrace();
            System.out.println(ue);
        }
        throw new NotFoundException("Resource with open library ID: " + open_library_id + " not found.");
        // return false;
    }

    public static String getDescription(){
        return description;
    }

    


}
