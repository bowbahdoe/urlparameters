package dev.mccue.urlparameters;

import java.util.Objects;

public record UrlParameter(
        String name,
        String value
) {
    public UrlParameter {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
    }
}
