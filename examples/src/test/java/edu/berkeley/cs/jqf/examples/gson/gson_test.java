package edu.berkeley.cs.jqf.examples.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
            // convert input string into object
            Object object = gson.fromJson(input, Object.class);

            // convert object back to string
            String conv_json = gson.toJson(object);

            // convert object to tree of jsonelements
            JsonElement json_tree = gson.toJsonTree(object);

            // convert tree back to string
            String conv_tree = gson.toJson(json_tree);

            // test if the strings are the same (they should be)
            // assertEquals(conv_json, conv_tree);

            // parse the input string to a parse tree of jsonelements
            JsonElement parse_tree = JsonParser.parseString(input);

            // convert the parse tree back to a string
            String conv_parsetree = gson.toJson(parse_tree);
            parse_tree.toString();

            // System.out.println("input: " + input + "\nConvert: " + conv_json +
            // "\nConverttree: " + conv_tree
            // + "\nConvert parse tree: " + conv_parsetree + "\n");

        } catch (JsonSyntaxException e) {
            Assume.assumeNoException(e);
        } catch (JsonIOException e) {
            Assume.assumeNoException(e);
        } catch (JsonParseException e) {
            Assume.assumeNoException(e);
        }
        // doesnt work, builder methode not found
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
