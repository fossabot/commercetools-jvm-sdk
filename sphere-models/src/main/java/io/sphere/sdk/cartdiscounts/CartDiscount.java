package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as=CartDiscountImpl.class)
public interface CartDiscount extends DefaultModel<CartDiscount> {
    String getCartPredicate();

    @Nullable
    LocalizedStrings getDescription();

    boolean isActive();

    LocalizedStrings getName();

    List<Reference<Object>> getReferences();

    boolean isRequiringDiscountCode();

    String getSortOrder();

    CartDiscountTarget getTarget();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    CartDiscountValue getValue();

    static String typeId(){
        return "cart-discount";
    }

    @Override
    default Reference<CartDiscount> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static TypeReference<CartDiscount> typeReference() {
        return new TypeReference<CartDiscount>() {
            @Override
            public String toString() {
                return "TypeReference<CartDiscount>";
            }
        };
    }
}
