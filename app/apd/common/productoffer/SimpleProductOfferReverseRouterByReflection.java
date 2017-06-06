package apd.common.productoffer;

import com.commercetools.sunrise.framework.reverserouters.AbstractReflectionReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.ParsedRoutes;
import com.commercetools.sunrise.framework.reverserouters.ReverseCaller;
import play.mvc.Call;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class SimpleProductOfferReverseRouterByReflection extends AbstractReflectionReverseRouter implements SimpleProductOfferReverseRouter {

    private final ReverseCaller productDetailPageCaller;
    private final ReverseCaller productOfferPageCaller;
    private final ReverseCaller searchProcessCaller;

    @Inject
    private SimpleProductOfferReverseRouterByReflection(final ParsedRoutes parsedRoutes) {
        productDetailPageCaller = getReverseCallerForSunriseRoute(PRODUCT_DETAIL_PAGE, parsedRoutes);
        productOfferPageCaller = getReverseCallerForSunriseRoute(PRODUCT_OFFER_PAGE, parsedRoutes);
        searchProcessCaller = getReverseCallerForSunriseRoute(SEARCH_PROCESS, parsedRoutes);
    }

    @Override
    public Call productDetailPageCall(final String languageTag, final String productIdentifier, final String productVariantIdentifier) {
        return productDetailPageCaller.call(languageTag, productIdentifier, productVariantIdentifier);
    }

    @Override
    public Call productOfferPageCall(final String languageTag, final String productIdentifiers) {
        return productOfferPageCaller.call(languageTag, productIdentifiers);
    }

    @Override
    public Call searchProcessCall(final String languageTag) {
        return searchProcessCaller.call(languageTag);
    }
}
