package controllers.productcatalog;

import java.util.concurrent.CompletionStage;

import javax.annotation.Nullable;
import javax.inject.Inject;

import play.mvc.Result;
import apd.productcatalog.productoffer.ProductListFinder;
import apd.productcatalog.productoffer.SunriseProductOfferController;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.productcatalog.productoverview.search.ProductOverviewSearchControllerComponentsSupplier;
import com.commercetools.sunrise.productcatalog.productoverview.viewmodels.ProductOverviewPageContentFactory;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        ProductOverviewSearchControllerComponentsSupplier.class
})
public final class ProductOfferController extends SunriseProductOfferController {

    @Inject
    public ProductOfferController(final ContentRenderer contentRenderer,
                                     final ProductListFinder productListFinder,
                                     final ProductOverviewPageContentFactory pageContentFactory) {
        super(contentRenderer, productListFinder, pageContentFactory);
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "pop";
    }

	@Override
	public CompletionStage<Result> handleNotFoundProduct() {
		// TODO Auto-generated method stub
		return null;
	}
}