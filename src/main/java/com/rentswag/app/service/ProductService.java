package com.rentswag.app.service;

import java.util.List;

import com.rentswag.app.model.Product;
import com.rentswag.app.model.ProductForm;
import com.rentswag.app.model.ProductInfo;

public interface ProductService {
	  public Product findProduct(String code);
	  public ProductInfo findProductInfo(String code) ;
	  //@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	   public void save(ProductForm productForm);
	   public List<Product> listOrderInfo();
	  
}
