package io.sphere.sdk.http;

import com.ning.http.client.*;

public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return AsyncHttpClientAdapterImpl.of(asyncHttpClient);
    }
}