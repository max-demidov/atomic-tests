package name.mdemidov.atomic.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Cleanup;
import lombok.val;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private Object json;

    /**
     * JSON reader provides an access point to data in JSON file.
     *
     * @param filename is a full path to JSON file
     */
    public JsonReader(String filename) {
        try {
            @Cleanup
            val fileReader = new FileReader(filename);
            json = new JSONParser().parse(fileReader);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON from file " + filename, e);
        }
    }

    /**
     * Provides well formatted and printable JSON.
     *
     * @return JSON as String
     */
    public String getJson() {
        val parser = new JsonParser();
        val element = parser.parse(json.toString());
        return GSON.toJson(element);
    }

    /**
     * Provides data of required type parsed from JSON.
     *
     * @param <T> is a required data type
     * @return data converted from JSON to <code>T</code>
     */
    public <T> T getData() {
        val text = getJson();
        val type = new TypeToken<T>() {
        }.getType();
        return GSON.fromJson(text, type);
    }
}
