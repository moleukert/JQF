package edu.berkeley.cs.jqf.examples.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
//import com.google.gson.ToNumberPolicy;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParseException;
import com.pholser.junit.quickcheck.From;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator;
import edu.berkeley.cs.jqf.fuzz.JQF;
import edu.berkeley.cs.jqf.fuzz.Fuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Assume;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class gson_test {

    private Gson gson = new Gson();
    // Gson custom = new GsonBuilder().create();

    @Fuzz
    public void fuzzJSONParser(@From(JsonGenerator.class) String input) {
        // public void fuzzJSONParser(@From(AsciiStringGenerator.class) String input) {
        // test standard deserialization with gson
        try {
            Object object = gson.fromJson(input, Object.class);
            String conv_json = gson.toJson(object);
            assertEquals("not equal", input, conv_json);
        } catch (JsonSyntaxException e) {
            Assume.assumeNoException(e);
        } catch (JsonIOException e) {
            Assume.assumeNoException(e);
        }

        // // test standard parsing to Tree with JsonParser
        try {
            JsonParser.parseString(input);
        } catch (JsonParseException e) {
            Assume.assumeNoException(e);
        }
        // // test deserialization with custom gson
        // try {
        // gson.fromJson(input, Object.class);
        // } catch (JsonSyntaxException e) {
        // Assume.assumeNoException(e);
        // } catch (JsonIOException e) {
        // Assume.assumeNoException(e);
        // }

    }
}
