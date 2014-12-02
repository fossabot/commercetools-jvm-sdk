package io.sphere.sdk.search;

import java.util.Locale;
import java.util.Optional;

public class LocalizedStringsSearchModel<T> extends SearchModelImpl<T> {

    public LocalizedStringsSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringSearchModel<T> locale(final Locale locale) {
        return new StringSearchModel<>(Optional.of(this), locale.toLanguageTag());
    }
}