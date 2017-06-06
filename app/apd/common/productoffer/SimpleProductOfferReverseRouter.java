package apd.common.productoffer;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(SimpleProductOfferReverseRouterByReflection.class)
public interface SimpleProductOfferReverseRouter {

    String PRODUCT_DETAIL_PAGE = "productDetailPageCall";

    Call productDetailPageCall(final String languageTag, final String productIdentifier, final String productVariantIdentifier);

    String PRODUCT_OFFER_PAGE = "productOfferPageCall";

    Call productOfferPageCall(final String languageTag, final String productIdentifiers);

    String SEARCH_PROCESS = "searchProcessCall";

    Call searchProcessCall(final String languageTag);
}
