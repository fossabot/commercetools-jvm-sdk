package de.commercetools.internal.filters;

import de.commercetools.sphere.client.filters.UserInputFilter;

import java.util.Map;

import static de.commercetools.internal.util.QueryStringConstruction.clearParam;
import static de.commercetools.internal.util.QueryStringConstruction.toQueryString;

/** Helper base class for implementations of {@link de.commercetools.sphere.client.filters.UserInputFilter}. */
public abstract class UserInputFilterBase<T> implements UserInputFilter<T> {
    /** Name of the query parameter representing this filter. */
    protected String queryParam;

    protected UserInputFilterBase(String queryParam) {
        this.queryParam = queryParam;
    }
    public String getQueryParamName() {
        return this.queryParam;
    }

    @Override public final String getClearLink(Map<String,String[]> queryString) {
        return toQueryString(clearParam(queryParam, queryString));
    }
    @Override public final boolean isSet(Map<String,String[]> queryString) {
        return parseValue(queryString) != null;
    }
}
