package io.sphere.sdk.client;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static org.assertj.core.api.Assertions.assertThat;

public class SphereConstantAccessTokenSupplierImplTest extends IntegrationTest {
    @Test
    public void requestsArePossible() throws Exception {
        withCategory(client(), category -> {
            final CategoryQuery categoryQuery = CategoryQuery.of();
            final int expected = client().execute(categoryQuery).getTotal();
            final SphereApiConfig apiConfig = SphereApiConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getApiUrl());

            final SphereAuthConfig authConfig = SphereAuthConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getClientId(), getSphereClientConfig().getClientSecret(), getSphereClientConfig().getAuthUrl());

            final SphereAccessTokenSupplier fixedTokenSupplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(authConfig, newHttpClient(), true);
            final SphereClient oneTokenClient = SphereClientFactory.of().createClient(apiConfig, fixedTokenSupplier);
            final int actual = oneTokenClient.execute(categoryQuery).toCompletableFuture().join().getTotal();
            assertThat(actual).isEqualTo(expected);
            oneTokenClient.close();
        });
    }
}