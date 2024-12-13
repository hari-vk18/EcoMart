package com.project.EcoMart.Service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.EcoMart.Model.Category;
import com.project.EcoMart.Model.Image;
import com.project.EcoMart.Model.Product;
import com.project.EcoMart.dtos.ImageDto;
import com.project.EcoMart.dtos.ProductDto;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.repository.CategoryRepository;
import com.project.EcoMart.repository.ImageRepository;
import com.project.EcoMart.repository.ProductRepository;
import com.project.EcoMart.request.AddProductRequest;
import com.project.EcoMart.request.UpdateProductRequest;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Product addProduct(AddProductRequest request) {
		// TODO Auto-generated method stub

		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private Product createProduct(AddProductRequest request, Category category) {

		return new Product(request.getName(), request.getBrand(), request.getInventory(), request.getPrice(),
				request.getDescription(), category);

	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
			throw new ResourceNotFoundException("Product not found!");
		});
	}

	@Override
	public Product updateProductById(UpdateProductRequest product, Long productId) {
		// TODO Auto-generated method stub

		return productRepository.findById(productId).map(existingProd -> updateExistingProduct(existingProd, product))
				.map(productRepository::save).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	private Product updateExistingProduct(Product existingProd, UpdateProductRequest Request) {
		existingProd.setName(Request.getName());
		existingProd.setBrand(Request.getBrand());
		existingProd.setPrice(Request.getPrice());
		existingProd.setDescription(Request.getDescription());
		existingProd.setInventory(Request.getInventory());

		Category category = categoryRepository.findByName(Request.getCategory().getName());
		existingProd.setCategory(category);

		return existingProd;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByNameAndBrand(String name, String brand) {
		// TODO Auto-generated method stub
		return productRepository.findByNameAndBrand(name, brand);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return productRepository.countByBrandAndName(brand, name);
	}

	@Override
	public List<ProductDto> getConvertedDto(List<Product> products) {
		return products.stream().map(this::convertToDto).toList();
	}

	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto dto = mapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream().map(image -> mapper.map(image, ImageDto.class)).toList();
		dto.setImages(imageDtos);
		return dto;
	}

}
