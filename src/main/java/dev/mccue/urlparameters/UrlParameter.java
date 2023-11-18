package dev.mccue.urlparameters;

import com.uwyn.urlencoder.UrlEncoder;

import java.util.Objects;

public record UrlParameter(
        String name,
        String value
) {
    public UrlParameter {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
    }

    @Override
    public String toString() {
        return UrlEncoder.encode(name) + "=" + UrlEncoder.encode(value);
    }
}
