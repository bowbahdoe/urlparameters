package dev.mccue.urlparameters.test;

import dev.mccue.urlparameters.UrlParameter;
import dev.mccue.urlparameters.UrlParameters;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlParametersTest {
    @Test
    public void testBlankUri() {
        assertEquals(new UrlParameters(List.of()), UrlParameters.parse(URI.create("/")));
    }

    @Test
    public void testAFewParams() {
        assertEquals(
                new UrlParameters(List.of(new UrlParameter("a", "b"))),
                UrlParameters.parse(URI.create("/?a=b"))
        );
    }

    @Test
    public void testUrlEncodedValue() {
        assertEquals(
                new UrlParameters(List.of(
                    new UrlParameter("a", "b"),
                    new UrlParameter("c", "d e")
                )),
                UrlParameters.parse(URI.create("/?a=b&c=d%20e"))
        );
    }

    @Test
    public void testEndOfPath() {
        assertEquals(new UrlParameters(List.of(
                new UrlParameter("a", "b"),
                new UrlParameter("c", "d e")
        )), UrlParameters.parse(URI.create("/some/path?a=b&c=d%20e")));
        assertEquals(
                new UrlParameters(List.of(
                        new UrlParameter("a", "b"),
                        new UrlParameter("c", "d e")
                )),
                UrlParameters.parse(URI.create("/some/path/?a=b&c=d%20e"))
        );
    }

    @Test
    public void testFirstValue() {
        var params = UrlParameters.parse("name=bob&age=42&name=susan");
        assertEquals(Optional.of("bob"), params.firstValue("name"));
        assertEquals(Optional.of("42"), params.firstValue("age"));
        assertEquals(Optional.empty(), params.firstValue("tall"));
    }

    @Test
    public void testLastValue() {
        var params = UrlParameters.parse("name=bob&age=42&name=susan");
        assertEquals(Optional.of("susan"), params.lastValue("name"));
        assertEquals(Optional.of("42"), params.lastValue("age"));
        assertEquals(Optional.empty(), params.lastValue("tall"));
    }

    @Test
    public void testAllValues() {
        var params = UrlParameters.parse("name=bob&age=42&name=susan");
        assertEquals(List.of("bob", "susan"), params.allValues("name"));
        assertEquals(List.of("42"), params.allValues("age"));
        assertEquals(List.of(), params.allValues("tall"));
    }

    @Test
    public void streamAndIterator() {
        var params = new UrlParameters(List.of(
            new UrlParameter("name", "bob"),
            new UrlParameter("age", "42"),
            new UrlParameter("twix", "for kids")
        ));

        assertEquals(
                params.parameters(),
                params.stream().toList()
        );

        var l = new ArrayList<UrlParameter>();
        for (var param : params) {
            l.add(param);
        }

        assertEquals(
                params.parameters(),
                l
        );
    }
}
