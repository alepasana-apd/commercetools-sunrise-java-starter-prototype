package apd.productcatalog.productoffer;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

public interface WithRequiredProduct {

    ProductListFinder getProductListFinder();

    default CompletionStage<Result> requireProduct(final List<String> identifiers, final Function<PagedSearchResult<ProductProjection>, CompletionStage<Result>> nextAction) {
        return getProductListFinder().apply(identifiers).thenComposeAsync(nextAction, HttpExecution.defaultContext());                      
    }

    CompletionStage<Result> handleNotFoundProduct();
}


