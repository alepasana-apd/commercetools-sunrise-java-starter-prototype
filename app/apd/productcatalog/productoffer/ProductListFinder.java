package apd.productcatalog.productoffer;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import com.commercetools.sunrise.framework.controllers.ResourceFinder;
import com.google.inject.ImplementedBy;

@ImplementedBy(ProductListFinderById.class)
@FunctionalInterface
public interface ProductListFinder extends ResourceFinder, Function<List<String>, CompletionStage<PagedSearchResult<ProductProjection>>> {
	
    CompletionStage<PagedSearchResult<ProductProjection>> apply(final List<String> identifiers);
    
}
