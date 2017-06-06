package apd.productcatalog.productoffer;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.PagedSearchResult;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.commercetools.sunrise.framework.hooks.HookRunner;

public class ProductListFinderById extends AbstractProductSearchExecutor implements ProductListFinder {

    private final PriceSelection priceSelection;

    @Inject
    protected ProductListFinderById(final SphereClient sphereClient, final HookRunner hookRunner, final PriceSelection priceSelection) {
        super(sphereClient, hookRunner);
        this.priceSelection = priceSelection;
    }

    @Override
    public CompletionStage<PagedSearchResult<ProductProjection>> apply(final List<String> ids) {
        return executeRequest(buildRequest(ids));
    }

    protected ProductProjectionSearch buildRequest(final List<String> ids) {
        return ProductProjectionSearch.ofCurrent()
                .withQueryFilters(m -> m.id().isIn(ids))
                .withPriceSelection(priceSelection);
    }
}
