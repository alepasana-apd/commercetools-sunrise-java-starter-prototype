package controllers.myaccount;

import io.sphere.sdk.customers.CustomerSignInResult;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import play.data.FormFactory;
import play.mvc.Result;
import apd.common.productoffer.ProductOfferReverseRouter;
import apd.common.productoffer.SimpleProductOfferReverseRouter;
import apd.service.ProductService;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.authentication.login.LogInControllerAction;
import com.commercetools.sunrise.myaccount.authentication.login.LogInFormData;
import com.commercetools.sunrise.myaccount.authentication.login.SunriseLogInController;
import com.commercetools.sunrise.myaccount.authentication.login.viewmodels.LogInPageContentFactory;
import com.commercetools.sunrise.sessions.cart.CartOperationsControllerComponentSupplier;
import com.commercetools.sunrise.sessions.customer.CustomerOperationsControllerComponentSupplier;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        CustomerOperationsControllerComponentSupplier.class,
        CartOperationsControllerComponentSupplier.class
})
public final class FbLogInController extends SunriseLogInController {

    private final SimpleProductOfferReverseRouter simpleProductOfferReverseRouter;
    private final ProductService productService;
    private final ProductOfferReverseRouter productOfferReverseRouter;
    
    @Inject
    public FbLogInController(final ContentRenderer contentRenderer,
                           final FormFactory formFactory,
                           final LogInFormData formData,
                           final LogInControllerAction controllerAction,
                           final LogInPageContentFactory pageContentFactory,
                           final SimpleProductOfferReverseRouter simpleProductOfferReverseRouter,
                           final ProductOfferReverseRouter productOfferReverseRouter,
                           final ProductService productService) {
        super(contentRenderer, formFactory, formData, controllerAction, pageContentFactory);
        this.simpleProductOfferReverseRouter = simpleProductOfferReverseRouter;
        this.productService = productService;
        this.productOfferReverseRouter = productOfferReverseRouter;
        
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "my-account-login";
    }
    
    @Override
    public CompletionStage<Result> okResultWithPageContent(
    		PageContent pageContent) {
    	List<String> ids = null;
    	try {
    		ids = productService.queryProduct();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return redirectToCall(simpleProductOfferReverseRouter.productOfferPageCall(productOfferReverseRouter.locale().toLanguageTag(), StringUtils.join(ids.toArray(), ",")));
    }
    

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerSignInResult result, final LogInFormData formData) {
    	List<String> ids = null;
    	try {
			ids = productService.queryProduct();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return redirectToCall(simpleProductOfferReverseRouter.productOfferPageCall(productOfferReverseRouter.locale().toLanguageTag(), StringUtils.join(ids.toArray(), ",")));
    }
}
