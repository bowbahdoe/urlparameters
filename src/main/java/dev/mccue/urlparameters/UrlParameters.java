package dev.mccue.urlparameters;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record UrlParameters(List<UrlParameter> parameters)
        implements Iterable<UrlParameter> {
    public UrlParameters(List<UrlParameter> parameters) {
        this.parameters = List.copyOf(parameters);
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
                                        URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                                        ""
                                ));
                            } else if (pair.length == 2) {
                                cb.accept(new UrlParameter(
                                        URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                                        URLDecoder.decode(pair[1], StandardCharsets.UTF_8)
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
}
