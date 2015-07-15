package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

public interface Fetch<T> extends SphereRequest<T> {
    @Override
    T deserialize(final HttpResponse httpResponse);
}
