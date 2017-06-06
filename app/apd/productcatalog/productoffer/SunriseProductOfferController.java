package apd.productcatalog.productoffer;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import play.mvc.Result;
import apd.common.productoffer.ProductOfferReverseRouter;

import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.productcatalog.productoverview.ProductsWithCategory;
import com.commercetools.sunrise.productcatalog.productoverview.viewmodels.ProductOverviewPageContentFactory;

/**
 * Controller to show the information about a single product.
 * Loads a {@link ProductProjection} and the selected {@link ProductVariant}.
 */
public abstract class SunriseProductOfferController  extends SunriseContentController implements WithQueryFlow<ProductsWithCategory>, WithRequiredProduct {

    private final ProductListFinder productListFinder;
    private final ProductOverviewPageContentFactory productOverviewPageContentFactory;

    protected SunriseProductOfferController(final ContentRenderer contentRenderer,
                                             final ProductListFinder productListFinder, 
                                             final ProductOverviewPageContentFactory productOverviewPageContentFactory) {
        super(contentRenderer);
        this.productListFinder = productListFinder;
        this.productOverviewPageContentFactory = productOverviewPageContentFactory;
    }


    public final ProductListFinder getProductListFinder() {
        return productListFinder;
    }

    @EnableHooks
    @SunriseRoute(ProductOfferReverseRouter.PRODUCT_OFFER_PAGE)
    public CompletionStage<Result> show(final String languageTag, final String productIdentifiers) {
    	List<String> identifiers = Arrays.asList(productIdentifiers.split(","));
    	
        return requireProduct(identifiers, products -> showPage(ProductsWithCategory.of(products)));
    }    

    @Override
    public PageContent createPageContent(final ProductsWithCategory productsWithCategory) {
        return productOverviewPageContentFactory.create(productsWithCategory);
    }
}