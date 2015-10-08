package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import org.junit.Test;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFacetsIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().price().centAmount().faceted().byGreaterThanOrEqualTo(0L);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getRangeFacetResult(facetExpr).getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1));
    }

    @Test
    public void termFacetsAreParsed() throws Exception {
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult termFacetResult = result.getTermFacetResult(facetExpr);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).faceted().byGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final RangeStats rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualTo("36.0");
        assertThat(rangeStats.getMax()).isEqualTo("46.0");
        assertThat(rangeStats.getSum()).isEqualTo("246.0");
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().byTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getFilteredFacetResult(facetExpr).getCount()).isEqualTo(2);
    }

    @Test
    public void simpleFacetsAreParsed() throws Exception {
        final String facetPath = "variants.attributes." + ATTR_NAME_COLOR;
        final FacetExpression<ProductProjection> facetExpr = FacetExpression.of(facetPath);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult termFacetResult = (TermFacetResult) result.getFacetResult(facetPath);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void termFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().withAlias(alias).byAllTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult termFacetResult = result.getTermFacetResult(facetExpr);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void rangeFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).faceted().withAlias(alias).byGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        final RangeStats rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualTo("36.0");
        assertThat(rangeStats.getMax()).isEqualTo("46.0");
        assertThat(rangeStats.getSum()).isEqualTo("246.0");
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FacetExpression<ProductProjection> facetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().withAlias(alias).byTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        assertThat(result.getFilteredFacetResult(facetExpr).getCount()).isEqualTo(2);
    }
}
