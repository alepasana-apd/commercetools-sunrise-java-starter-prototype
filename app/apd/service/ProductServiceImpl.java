package apd.service;

import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.joining;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductProjectionQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.commercetools.sunrise.framework.injection.RequestScoped;

@RequestScoped
public class ProductServiceImpl implements ProductService{

	private final SphereClientConfig sphereClientConfig;

	@Inject
	public ProductServiceImpl(final SphereClientConfig sphereClientConfig) {
		this.sphereClientConfig = sphereClientConfig;

	}

	@Override
	public List<String> queryProduct() throws Exception {

		final ProductProjectionQueryModel queryModel = ProductProjectionQueryModel.of();
		List<String> productIds = new ArrayList<String>();
		
		try(final BlockingSphereClient client = createCommercetoolsClient()) {
			final ProductProjectionQuery searchRequest =
					ProductProjectionQuery.ofCurrent()
					.withPredicates(m -> m.name().locale(ENGLISH).isPresent())
					//.withPredicates(queryModel.id().is("a44d480a-ba79-4260-85be-328625b1341b"))
					//.withPredicates(m -> m.id().is("a44d480a-ba79-4260-85be-328625b1341b"))                    		
					.withExpansionPaths(m -> m.categories())
					.withLimit(10)
					.withSort(m -> m.createdAt().sort().desc());

			final PagedQueryResult<ProductProjection> queryResult =
					client.executeBlocking(searchRequest);

			for (final ProductProjection product : queryResult.getResults()) {
				final String output = productProjectionToString(product);
				System.out.println("found product " + output);
				
				productIds.add(product.getId());
			}
		}
		
		return productIds;
	}

	private String productProjectionToString(final ProductProjection product) {
		final String name = product.getName().get(ENGLISH); 
		final String id = product.getId();
		final List<ProductVariant> variants = product.getAllVariants();

		final String categoryNamesString = product.getCategories()
				.stream()
				.filter(ref -> ref.getObj() != null)
				.map(categoryReference -> {
					final Category category = categoryReference.getObj();
					return category.getName().find(ENGLISH).orElse("name unknown");
				})
				.collect(joining(", "));

		/*
		for(ProductVariant variant : variants){
			System.out.println(">> attributeName " + variant.getSku() +  variant.getAttribute("custom-weight"));
		}
		*/
		return "ID: " + id + " : " + name + " in categories " + categoryNamesString;
	}

	private BlockingSphereClient createCommercetoolsClient() throws IOException {
		final SphereClient underlyingClient = SphereClientFactory.of().createClient(sphereClientConfig);//this client only works asynchronous
		return BlockingSphereClient.of(underlyingClient, 10, TimeUnit.SECONDS);
	}


}
