package io.sphere.sdk.queries;

public interface StringQueryModel<T> {
    QueryPredicate<T> is(String s);

    QueryPredicate<T> isNot(String s);

    QueryPredicate<T> isIn(String arg0, String... args);

    QueryPredicate<T> isIn(final Iterable<String> args);

    QueryPredicate<T> isGreaterThan(final String value);

    QueryPredicate<T> isLessThan(final String value);

    QueryPredicate<T> isLessThanOrEquals(final String value);

    QueryPredicate<T> isGreaterThanOrEqualTo(final String value);

    QueryPredicate<T> isNotIn(final Iterable<String> args);

    QueryPredicate<T> isNotIn(final String arg0, final String... args);

    QueryPredicate<T> isPresent();

    QueryPredicate<T> isNotPresent();
}
