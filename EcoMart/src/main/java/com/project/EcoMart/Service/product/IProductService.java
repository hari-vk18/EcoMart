package com.project.EcoMart.Service.product;

import java.util.List;

import com.project.EcoMart.Model.Product;
import com.project.EcoMart.request.AddProductRequest;
import com.project.EcoMart.request.UpdateProductRequest;

public interface IProductService {

	Product addProduct(AddProductRequest product);

	Product getProductById(Long id);

	void deleteProductById(Long id);

	Product updateProductById(UpdateProductRequest product, Long productId);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String category);

	List<Product> getProductsByBrand(String brand);

	List<Product> getProductsByCategoryAndBrand(String category, String brand);

	List<Product> getProductsByName(String name);

	List<Product> getProductsByNameAndBrand(String name, String brand);

	Long countProductsByBrandAndName(String brand, String name);

}
