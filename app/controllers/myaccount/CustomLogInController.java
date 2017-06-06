package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.framework.reverserouters.myaccount.mydetails.MyPersonalDetailsReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.authentication.login.LogInControllerAction;
import com.commercetools.sunrise.myaccount.authentication.login.LogInFormData;
import com.commercetools.sunrise.myaccount.authentication.login.SunriseLogInController;
import com.commercetools.sunrise.myaccount.authentication.login.viewmodels.LogInPageContentFactory;
import com.commercetools.sunrise.sessions.cart.CartOperationsControllerComponentSupplier;
import com.commercetools.sunrise.sessions.customer.CustomerOperationsControllerComponentSupplier;

import io.sphere.sdk.customers.CustomerSignInResult;
import play.data.FormFactory;
import play.mvc.Http.Request;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;

import java.util.concurrent.CompletionStage;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        CustomerOperationsControllerComponentSupplier.class,
        CartOperationsControllerComponentSupplier.class
})
public final class CustomLogInController extends SunriseLogInController {

    private final MyPersonalDetailsReverseRouter myPersonalDetailsReverseRouter;

    @Inject
    public CustomLogInController(final ContentRenderer contentRenderer,
                           final FormFactory formFactory,
                           final LogInFormData formData,
                           final LogInControllerAction controllerAction,
                           final LogInPageContentFactory pageContentFactory,
                           final MyPersonalDetailsReverseRouter myPersonalDetailsReverseRouter) {
        super(contentRenderer, formFactory, formData, controllerAction, pageContentFactory);
        this.myPersonalDetailsReverseRouter = myPersonalDetailsReverseRouter;
        Request request = request();
        String appId = request.getQueryString("app_id");
        System.out.println(">> CustomLogInController [appid: " + appId + "]");
    }


    @Nullable
    @Override
    public String getTemplateName() {
    	System.out.println(">> custom-my-account-login");
        return "custom-my-account-login";
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final CustomerSignInResult result, final LogInFormData formData) {
    	System.out.println(">> handleSuccessfulAction");
        return redirectToCall(myPersonalDetailsReverseRouter.myPersonalDetailsPageCall());
    }
}
