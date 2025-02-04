package edu.berkeley.cs.jqf.examples.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;

import com.pholser.junit.quickcheck.From;

import edu.berkeley.cs.jqf.examples.common.AsciiStringGenerator;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_mut;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_nomut;
import edu.berkeley.cs.jqf.fuzz.JQF;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import java.util.List;

import java.util.Map;

import static mjson.Json.factory;
import static mjson.Json.make;
import static org.junit.Assert.assertEquals;

import org.junit.Assume;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class jackson_test {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Fuzz
    public void fuzzJSONParser_mut(@From(JsonGenerator_mut.class) String input) {
        try {
            // JSON String to JAVA Object and back
            Object object = objectMapper.readValue(input, Object.class);
            String stringfromObject = objectMapper.writeValueAsString(object);
            // JSON to JAVA Object
            Map<String, Object> jsonMap = objectMapper.readValue(input, Map.class);
            // JAVA Object to JSon
            String jsonString = objectMapper.writeValueAsString(jsonMap);
            // JSON String to JAVA Map
            Map<String, Object> map = objectMapper.readValue(input, new TypeReference<Map<String, Object>>() {
            });
            // Deserialize input string to a JsonNode
            JsonNode jsonNode = objectMapper.readTree(input);

            // Serialize JsonNode back to a string
            String conv_json = objectMapper.writeValueAsString(jsonNode);

            String prettyJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            // JSON STRING to List and back
            List<String> stringList = (List<String>) objectMapper.readValue(input, new TypeReference<List<String>>() {
            });
            String stringfromList = objectMapper.writeValueAsString(stringList);

            // Test if the strings are the same
            assertEquals(jsonNode, stringfromList);

            // Convert JsonNode to a tree of JsonNodes
            JsonNode jsonTree = jsonNode.deepCopy();

            // Serialize JsonNode tree back to a string
            String conv_tree = objectMapper.writeValueAsString(jsonTree);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        } catch (JsonParseException e) {
            Assume.assumeNoException(e);
        } catch (JsonMappingException e) {

        } catch (JsonProcessingException e) {
            Assume.assumeNoException(e);
        } catch (IOException e) {
            Assume.assumeNoException(e);
        }

    }

    @Fuzz
    public void fuzzJSONParser_ascii(@From(AsciiStringGenerator.class) String input) {
        try {
            // JSON String to JAVA Object and back
            Object object = objectMapper.readValue(input, Object.class);
            String stringfromObject = objectMapper.writeValueAsString(object);
            // JSON to JAVA Object
            Map<String, Object> jsonMap = objectMapper.readValue(input, Map.class);
            // JAVA Object to JSon
            String jsonString = objectMapper.writeValueAsString(jsonMap);
            // JSON String to JAVA Map
            Map<String, Object> map = objectMapper.readValue(input, new TypeReference<Map<String, Object>>() {
            });
            // Deserialize input string to a JsonNode
            JsonNode jsonNode = objectMapper.readTree(input);

            // Serialize JsonNode back to a string
            String conv_json = objectMapper.writeValueAsString(jsonNode);

            String prettyJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            // JSON STRING to List and back
            List<String> stringList = (List<String>) objectMapper.readValue(input, new TypeReference<List<String>>() {
            });
            String stringfromList = objectMapper.writeValueAsString(stringList);

            // JSON String to XML
            // Test if the strings are the same
            assertEquals(jsonNode, stringfromList);

            // Convert JsonNode to a tree of JsonNodes
            JsonNode jsonTree = jsonNode.deepCopy();

            // Serialize JsonNode tree back to a string
            String conv_tree = objectMapper.writeValueAsString(jsonTree);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        } catch (JsonParseException e) {
            Assume.assumeNoException(e);
        } catch (JsonMappingException e) {

        } catch (JsonProcessingException e) {
            Assume.assumeNoException(e);
        } catch (IOException e) {
            Assume.assumeNoException(e);
        }

    }

    @Fuzz
    public void fuzzJSONParser_nomut(@From(JsonGenerator_nomut.class) String input) {
        try {
            // JSON String to JAVA Object and back
            Object object = objectMapper.readValue(input, Object.class);
            String stringfromObject = objectMapper.writeValueAsString(object);
            // JSON to JAVA Object
            Map<String, Object> jsonMap = objectMapper.readValue(input, Map.class);
            // JAVA Object to JSon
            String jsonString = objectMapper.writeValueAsString(jsonMap);
            // JSON String to JAVA Map
            Map<String, Object> map = objectMapper.readValue(input, new TypeReference<Map<String, Object>>() {
            });
            // Deserialize input string to a JsonNode
            JsonNode jsonNode = objectMapper.readTree(input);

            // Serialize JsonNode back to a string
            String conv_json = objectMapper.writeValueAsString(jsonNode);

            String prettyJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            // JSON STRING to List and back
            List<String> stringList = (List<String>) objectMapper.readValue(input, new TypeReference<List<String>>() {
            });
            String stringfromList = objectMapper.writeValueAsString(stringList);

            // JSON String to XML
            // Test if the strings are the same
            assertEquals(jsonNode, stringfromList);

            // Convert JsonNode to a tree of JsonNodes
            JsonNode jsonTree = jsonNode.deepCopy();

            // Serialize JsonNode tree back to a string
            String conv_tree = objectMapper.writeValueAsString(jsonTree);

        } catch (ClassCastException e) {
        } catch (AssertionError e) {
        } catch (JsonParseException e) {
            Assume.assumeNoException(e);
        } catch (JsonMappingException e) {

        } catch (JsonProcessingException e) {
            Assume.assumeNoException(e);
        } catch (IOException e) {
            Assume.assumeNoException(e);
        }

    }
}
