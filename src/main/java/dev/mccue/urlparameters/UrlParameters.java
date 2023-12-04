package dev.mccue.urlparameters;

import com.uwyn.urlencoder.UrlEncoder;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record UrlParameters(List<UrlParameter> parameters)
        implements Iterable<UrlParameter> {
    public UrlParameters(List<UrlParameter> parameters) {
        this.parameters = List.copyOf(parameters);
    }

    public UrlParameters(
            UrlParameter... parameters
    ) {
        this(Arrays.asList(parameters));
    }

    public UrlParameters(
            String k1, String v1
    ) {
        this(List.of(
                new UrlParameter(k1, v1)
        ));
    }

    public UrlParameters(
            String k1, String v1,
            String k2, String v2
    ) {
        this(List.of(
                new UrlParameter(k1, v1),
                new UrlParameter(k2, v2)
        ));
    }

    public UrlParameters(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3
    ) {
        this(List.of(
                new UrlParameter(k1, v1),
                new UrlParameter(k2, v2),
                new UrlParameter(k3, v3)
        ));
    }

    public UrlParameters(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4
    ) {
        this(List.of(
                new UrlParameter(k1, v1),
                new UrlParameter(k2, v2),
                new UrlParameter(k3, v3),
                new UrlParameter(k4, v4)
        ));
    }

    public UrlParameters(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5
    ) {
        this(List.of(
                new UrlParameter(k1, v1),
                new UrlParameter(k2, v2),
                new UrlParameter(k3, v3),
                new UrlParameter(k4, v4),
                new UrlParameter(k5, v5)
        ));
    }

    public UrlParameters(
            String k1, String v1,
            String k2, String v2,
            String k3, String v3,
            String k4, String v4,
            String k5, String v5,
            String k6, String v6
    ) {
        this(List.of(
                new UrlParameter(k1, v1),
                new UrlParameter(k2, v2),
                new UrlParameter(k3, v3),
                new UrlParameter(k4, v4),
                new UrlParameter(k5, v5),
                new UrlParameter(k6, v6)
        ));
    }

    public static UrlParameters parse(URI uri) {
        var params = uri.getRawQuery();
        return parse(params == null ? "" : params);
    }

    public static UrlParameters parse(String nameValuePairs) {
        return new UrlParameters(
                Arrays.stream(nameValuePairs.split("&"))
                        .map(pair -> pair.split("=", 2))
                        .mapMulti((String[] pair, Consumer<UrlParameter> cb) -> {
                            if (pair.length == 1) {
                                if ("".equals(pair[0])) {
                                    return;
                                }
                                cb.accept(new UrlParameter(
                                        UrlEncoder.decode(pair[0]),
                                        ""
                                ));
                            } else if (pair.length == 2) {
                                cb.accept(new UrlParameter(
                                        UrlEncoder.decode(pair[0]),
                                        UrlEncoder.decode(pair[1])
                                ));
                            }
                        })
                        .toList()
        );
    }

    @Override
    public Iterator<UrlParameter> iterator() {
        return parameters.iterator();
    }

    public Stream<UrlParameter> stream() {
        return parameters.stream();
    }

    public Optional<String> firstValue(String name) {
        for (var param : parameters) {
            if (param.name().equals(name)) {
                return Optional.of(param.value());
            }
        }
        return Optional.empty();
    }

    public Optional<String> lastValue(String name) {
        Optional<String> value = Optional.empty();
        for (var param : parameters) {
            if (param.name().equals(name)) {
                value = Optional.of(param.value());
            }
        }
        return value;
    }

    public List<String> allValues(String name) {
        var values = new ArrayList<String>();
        for (var param : parameters) {
            if (param.name().equals(name)) {
                values.add(param.value());
            }
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return parameters.stream()
                .map(UrlParameter::toString)
                .collect(Collectors.joining("&"));
    }
}
