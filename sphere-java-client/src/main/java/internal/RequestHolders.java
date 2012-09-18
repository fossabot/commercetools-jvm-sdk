package de.commercetools.internal;

import com.google.common.base.Strings;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;
import de.commercetools.sphere.client.BackendException;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.AsyncCompletionHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class RequestHolders {
    /** Executes request and parses JSON response into as given type. */
    public static <T> ListenableFuture<T> execute(final RequestHolder<T> requestHolder, final TypeReference<T> resultType) {
        try {
            if (Log.isTraceEnabled()) {
                Log.trace(requestHolder.getRawUrl());
                if (!Strings.isNullOrEmpty(requestHolder.getBody())) {
                    Log.trace(requestHolder.getBody());
                }
            }
            return requestHolder.executeRequest(new AsyncCompletionHandler<T>() {
                @Override
                public T onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() != 200) {
                        String message = String.format(
                                "The backend returned an error response: %s\n[%s]\n%s",
                                requestHolder.getRawUrl(),
                                response.getStatusCode(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message);
                        throw new BackendException(message);
                    } else {
                        ObjectMapper jsonParser = new ObjectMapper();
                        T parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), resultType);
                        if (Log.isTraceEnabled()) {
                            Log.trace(Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name())));
                        }
                        return parsed;
                    }
                }
            });
        } catch (Exception e) {
            throw new BackendException(e);
        }
    }
}
