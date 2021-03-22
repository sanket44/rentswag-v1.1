package com.rentswag.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentswag.app.dao.ProductDao;
import com.rentswag.app.model.Product;
import com.rentswag.app.model.ProductForm;
import com.rentswag.app.model.ProductInfo;
import com.rentswag.app.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
   
	@Autowired
	private ProductDao productDao;
	

	@Override
	public ProductInfo findProductInfo(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ProductForm productForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> listOrderInfo() {
		// TODO Auto-generated method stub
		return productDao.findAll();
	}

	@Override
	public Product findProduct(String code) {
		// TODO Auto-generated method stub
		return productDao.findProduct(code);
	}
	public List<Product> saveAllProducts(Iterable<Product> ProductList) {
		List<Product> response=(List<Product>)productDao.saveAll(ProductList);
		return response;
	}
	

}
