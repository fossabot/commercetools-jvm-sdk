package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;

import javax.annotation.Nullable;
import java.util.List;

public final class DiscountedPriceExpansionModel<T> extends ExpansionModel<T> {
    DiscountedPriceExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPathsHolder<T> discount() {
        return expansionPath("discount");
    }
}

