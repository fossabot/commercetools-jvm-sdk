package io.sphere.sdk.customers;

import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerDeleteCommand;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.SetCustomField;
import io.sphere.sdk.customers.commands.updateactions.SetCustomType;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.customers.CustomerFixtures.newCustomerDraft;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCustomFieldsTest extends IntegrationTest {
    @Test
    public void createCustomerWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();


            final CustomerDraft customerDraft = CustomerDraftBuilder.of(newCustomerDraft()).custom(customFieldsDraft).build();
            final Customer customer = execute(CustomerCreateCommand.of(customerDraft)).getCustomer();
            assertThat(customer.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

            final Customer updatedCustomer = execute(CustomerUpdateCommand.of(customer, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value")));
            assertThat(updatedCustomer.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a new value");

            //clean up
            execute(CustomerDeleteCommand.of(updatedCustomer));
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
           withCustomer(client(), customer -> {
               final HashMap<String, Object> fields = new HashMap<>();
               fields.put(STRING_FIELD_NAME, "hello");
               final Customer updatedCustomer = execute(CustomerUpdateCommand.of(customer, SetCustomType.ofTypeIdAndObjects(type.getId(), fields)));
               assertThat(updatedCustomer.getCustom().getType()).isEqualTo(type.toReference());
               assertThat(updatedCustomer.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

               final Customer updated2 = execute(CustomerUpdateCommand.of(updatedCustomer, SetCustomType.ofRemoveType()));
               assertThat(updated2.getCustom()).isNull();
           });
            return type;
        });
    }
}