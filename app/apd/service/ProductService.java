package apd.service;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProductServiceImpl.class)
public interface ProductService {
	
	public List<String> queryProduct() throws Exception;	
}
