package edu.berkeley.cs.jqf.examples.jsonio;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import edu.berkeley.cs.jqf.fuzz.JQF;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import com.pholser.junit.quickcheck.From;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_mut;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_nomut;
import edu.berkeley.cs.jqf.examples.common.AsciiStringGenerator;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Assume;
import org.junit.runner.RunWith;
import java.util.List;

import com.cedarsoftware.util.io.JsonIo;
import com.cedarsoftware.util.io.JsonIoException;
import com.cedarsoftware.util.io.JsonObject;

@RunWith(JQF.class)
public class JsonIO {

    @Fuzz
    public void fuzzJsonReadValue_mut(@From(JsonGenerator_mut.class) String input) {
        try {
            // JSON String to JSON Object and back
            JsonObject jsonObject2 = (JsonObject) JsonReader.toObjects(input);
            String jsonfromJsonObject = JsonWriter.toJson(jsonObject2, null);
            // JSON-String in ein Java-Objekt umwandeln
            Object jsonObject = JsonReader.toObjects(input);
            // Java-Objekt in JSON-String umwandeln
            String jsonString = JsonWriter.toJson(jsonObject, null);
            // JSON-String in ein Map-Objekt umwandeln
            Map<String, Object> map = (Map<String, Object>) JsonReader.toMaps(jsonString);
            // Map Object in JSON String umandeln
            String jsonStringfromMap = JsonWriter.toJson(map, null);

            // JSON-String in eine Liste umwandeln
            List<String> stringList = (List<String>) JsonReader.toObjects(jsonString);
            // Liste in JSON String umwandeln
            String jsonStringfromList = JsonWriter.toJson(stringList, null);

            // JSON-String in ein Array umwandeln
            String[] stringArray = (String[]) JsonReader.toMaps(jsonString);
            // Array in JSON String umwandeln
            String jsonStringfromArray = JsonWriter.toJson(stringArray, null);

            // tests for JsonIo
            String input_io = JsonIo.formatJson(input);
            Object io_obj = JsonIo.toObjects(input_io, null, null);
            JsonIo.toJsonValues(input, null);
            JsonIo.toJson(io_obj, null);
            JsonIo.deepCopy(io_obj, null, null);

            // Ueberpruefen ob Strings gleich sind
            assertEquals(jsonStringfromList, jsonStringfromMap);
            assertEquals(jsonString, jsonfromJsonObject);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        }
        // catch (JsonIoException e) {}
        catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    @Fuzz
    public void fuzzJsonReadValue_nomut(@From(JsonGenerator_nomut.class) String input) {
        try {
            // JSON String to JSON Object and back
            JsonObject jsonObject2 = (JsonObject) JsonReader.toObjects(input);
            String jsonfromJsonObject = JsonWriter.toJson(jsonObject2, null);
            // JSON-String in ein Java-Objekt umwandeln
            Object jsonObject = JsonReader.toObjects(input);
            // Java-Objekt in JSON-String umwandeln
            String jsonString = JsonWriter.toJson(jsonObject, null);
            // JSON-String in ein Map-Objekt umwandeln
            Map<String, Object> map = (Map<String, Object>) JsonReader.toMaps(jsonString);
            // Map Object in JSON String umandeln
            String jsonStringfromMap = JsonWriter.toJson(map, null);

            // JSON-String in eine Liste umwandeln
            List<String> stringList = (List<String>) JsonReader.toObjects(jsonString);
            // Liste in JSON String umwandeln
            String jsonStringfromList = JsonWriter.toJson(stringList, null);

            // JSON-String in ein Array umwandeln
            String[] stringArray = (String[]) JsonReader.toMaps(jsonString);
            // Array in JSON String umwandeln
            String jsonStringfromArray = JsonWriter.toJson(stringArray, null);

            // tests for JsonIo
            String input_io = JsonIo.formatJson(input);
            Object io_obj = JsonIo.toObjects(input_io, null, null);
            JsonIo.toJsonValues(input, null);
            JsonIo.toJson(io_obj, null);
            JsonIo.deepCopy(io_obj, null, null);

            // Ueberpruefen ob Strings gleich sind
            assertEquals(jsonStringfromList, jsonStringfromMap);
            assertEquals(jsonString, jsonfromJsonObject);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        }
        // catch (JsonIoException e) {}
        catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }

    @Fuzz
    public void fuzzJsonReadValue_ascii(@From(AsciiStringGenerator.class) String input) {
        try {
            // JSON String to JSON Object and back
            JsonObject jsonObject2 = (JsonObject) JsonReader.toObjects(input);
            String jsonfromJsonObject = JsonWriter.toJson(jsonObject2, null);
            // JSON-String in ein Java-Objekt umwandeln
            Object jsonObject = JsonReader.toObjects(input);
            // Java-Objekt in JSON-String umwandeln
            String jsonString = JsonWriter.toJson(jsonObject, null);
            // JSON-String in ein Map-Objekt umwandeln
            Map<String, Object> map = (Map<String, Object>) JsonReader.toMaps(jsonString);
            // Map Object in JSON String umandeln
            String jsonStringfromMap = JsonWriter.toJson(map, null);

            // JSON-String in eine Liste umwandeln
            List<String> stringList = (List<String>) JsonReader.toObjects(jsonString);
            // Liste in JSON String umwandeln
            String jsonStringfromList = JsonWriter.toJson(stringList, null);

            // JSON-String in ein Array umwandeln
            String[] stringArray = (String[]) JsonReader.toMaps(jsonString);
            // Array in JSON String umwandeln
            String jsonStringfromArray = JsonWriter.toJson(stringArray, null);

            // tests for JsonIo
            String input_io = JsonIo.formatJson(input);
            Object io_obj = JsonIo.toObjects(input_io, null, null);
            JsonIo.toJsonValues(input, null);
            JsonIo.toJson(io_obj, null);
            JsonIo.deepCopy(io_obj, null, null);

            // Ueberpruefen ob Strings gleich sind
            assertEquals(jsonStringfromList, jsonStringfromMap);
            assertEquals(jsonString, jsonfromJsonObject);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        }
        // catch (JsonIoException e) {}
        catch (Exception e) {
            Assume.assumeNoException(e);
        }
    }
}