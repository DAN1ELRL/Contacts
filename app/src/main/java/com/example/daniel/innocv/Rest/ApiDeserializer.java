package com.example.daniel.innocv.Rest;

import com.example.daniel.innocv.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by daniel on 13/02/16.
 */
public class ApiDeserializer implements JsonDeserializer<ApiResponse>{


    @Override
    public ApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ApiResponse response = new ApiResponse();

        if(json.isJsonArray()){
            JsonArray data = json.getAsJsonArray();
            if(data.size()>0){
                Type collectionType = new TypeToken<ArrayList<User>>(){}.getType();
                ArrayList<User> registros = (new Gson()).fromJson(data, collectionType);
                response.setUsersList(registros);
            }
        }else if(json.isJsonObject()){
            JsonObject data = json.getAsJsonObject();
            if(data!=null){
                Type collectionType = new TypeToken<User>(){}.getType();
                User registro = (new Gson()).fromJson(data, collectionType);
                response.setUser(registro);
            }
        }

        return response;
    }
}
