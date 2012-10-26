package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.shop.model.Product;

/** Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Creates a request that finds a product by id. */
    QueryRequest<Product> byId(String id);

    /** Creates a request that queries all products. */
    SearchRequest<Product> all();

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequest<Product> filtered(FilterExpression filter, FilterExpression... filters);

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequest<Product> filtered(Iterable<FilterExpression> filters);
}
