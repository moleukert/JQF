package edu.berkeley.cs.jqf.examples.mjson;

import mjson.Json;
import static mjson.Json.*;

import org.junit.Assert;
import java.util.Iterator;
import java.util.Map;
import org.junit.runner.notification.Failure;

import com.pholser.junit.quickcheck.From;
import edu.berkeley.cs.jqf.fuzz.JQF;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_mut;
import edu.berkeley.cs.jqf.examples.json.JsonGenerator_nomut;
import edu.berkeley.cs.jqf.examples.common.AsciiStringGenerator;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import org.junit.Assume;
import org.junit.runner.RunWith;

@RunWith(JQF.class)
public class mjson_test {

    @Fuzz
    public void fuzzJSONParser_mut(@From(JsonGenerator_mut.class) String input) {

        try {
            System.out.println(input);
            Json t = Json.make(input);
            Json t2 = Json.factory().string(input);
            Json tdup = t.dup();
            Assert.assertEquals(t, tdup);
            Assert.assertEquals(t, t2);

            Json.read(input);

        } catch (MalformedJsonException e) {
            Assume.assumeNoException(e);
        }

    }

    @Fuzz
    public void fuzzJSONParser_nomut(@From(JsonGenerator_nomut.class) String input) {

        try {

            Json t = Json.make(input);
            Json t2 = Json.factory().string(input);
            Json tdup = t.dup();
            Assert.assertEquals(t, tdup);
            Assert.assertEquals(t, t2);

            Json.read(input);

            // Json obj = object();
            // Json s = make(input);
            // obj.set("test", s);
            // Assert.assertTrue(obj == s.up());

        } catch (MalformedJsonException e) {
            Assume.assumeNoException(e);
        }

    }

    @Fuzz
    public void fuzzJSONParser_ascii(@From(AsciiStringGenerator.class) String input) {

        try {
            Json t = Json.make(input);
            Json t2 = Json.factory().string(input);
            Json tdup = t.dup();
            Assert.assertEquals(t, tdup);
            Assert.assertEquals(t, t2);

            Json.read(input);

            // Json obj = object();
            // Json s = make(input);
            // obj.set("test", s);
            // Assert.assertTrue(obj == s.up());

        } catch (MalformedJsonException e) {
            Assume.assumeNoException(e);
        }

    }
}