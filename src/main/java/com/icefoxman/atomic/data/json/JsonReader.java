package com.icefoxman.atomic.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Cleanup;
import lombok.val;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private JSONObject json;

    public JsonReader(String filename) {
        try {
            @Cleanup
            val fileReader = new FileReader(filename);
            json = (JSONObject) new JSONParser().parse(fileReader);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON from file " + filename, e);
        }
    }

    public String getJson() {
        val parser = new JsonParser();
        val element = parser.parse(json.toString());
        return GSON.toJson(element);
    }

    public <T> T getData() {
        val text = getJson();
        val type = new TypeToken<T>() {
        }.getType();
        return GSON.fromJson(text, type);
    }
}
