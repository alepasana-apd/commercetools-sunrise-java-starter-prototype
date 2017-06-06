package apd.common.productoffer;

import com.commercetools.sunrise.framework.reverserouters.AbstractLinksControllerComponent;
import com.commercetools.sunrise.framework.viewmodels.meta.PageMeta;

import javax.inject.Inject;

public class ProductOfferLinksControllerComponent extends AbstractLinksControllerComponent<ProductOfferReverseRouter> {

    private final ProductOfferReverseRouter reverseRouter;

    @Inject
    protected ProductOfferLinksControllerComponent(final ProductOfferReverseRouter reverseRouter) {
        this.reverseRouter = reverseRouter;
    }

    @Override
    public final ProductOfferReverseRouter getReverseRouter() {
        return reverseRouter;
    }

    @Override
    protected void addLinksToPage(final PageMeta meta, final ProductOfferReverseRouter reverseRouter) {
        meta.addHalLink(reverseRouter.searchProcessCall(), "search");
    }
}