package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public class LineItemExpansionModel<T> extends ExpansionModel<T> {
    LineItemExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    LineItemExpansionModel() {
        super();
    }

    public ExpansionPath<T> supplyChannel() {
        return expansionPath("supplyChannel");
    }

    public ExpansionPath<T> distributionChannel() {
        return expansionPath("distributionChannel");
    }

    public ItemStateExpansionModel<T> state() {
        return state("*");
    }

    public ItemStateExpansionModel<T> state(final int index) {
        return state("" + index);
    }

    private ItemStateExpansionModel<T> state(final String s) {
        return new ItemStateExpansionModel<>(pathExpressionOption(), "state[" + s + "]");
    }
}