package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartFetchByCustomerId;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.*;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class CustomerDeleteByIdCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final CustomerSignInResult result = client().execute(CustomerCreateCommand.of(newCustomerDraft()));
        final Customer customer = result.getCustomer();
        execute(CustomerDeleteByIdCommand.of(customer));
        final Optional<Cart> cartOptional = execute(CartFetchByCustomerId.of(customer));
        assertThat(cartOptional).isAbsent();
    }
}