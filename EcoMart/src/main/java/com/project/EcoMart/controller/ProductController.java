package com.project.EcoMart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.EcoMart.Model.Product;
import com.project.EcoMart.Service.product.IProductService;
import com.project.EcoMart.dtos.ProductDto;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.request.AddProductRequest;
import com.project.EcoMart.request.UpdateProductRequest;
import com.project.EcoMart.response.ApiResponse;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	@Autowired
	IProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProduct() {
		List<Product> products = productService.getAllProducts();
		List<ProductDto> productDtos = productService.getConvertedDto(products);
		return ResponseEntity.ok(new ApiResponse("Found!", productDtos));
	}

	@GetMapping("/product/{id}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
		try {
			Product product = productService.getProductById(id);
			ProductDto dto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Found!", dto));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
		try {
			Product theProduct = productService.addProduct(product);
			ProductDto dto = productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("Add Product Succes!", dto));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/product/{id}/update")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
		try {
			Product product = productService.updateProductById(request, id);
			ProductDto dto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Update Success!", dto));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/product/{id}/delete")
	public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id) {
		try {
			productService.deleteProductById(id);
			return ResponseEntity.ok(new ApiResponse("Delete Success!", id));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{name}/products")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
		try {
			List<Product> products = productService.getProductsByName(name);
			if (products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No product found!", null));
			}
			List<ProductDto> dto = productService.getConvertedDto(products);
			return ResponseEntity.ok(new ApiResponse("Found Success!", dto));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
		try {
			List<Product> products = productService.getProductsByBrand(brand);
			if (products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No product found!", null));
			}
			List<ProductDto> dto = productService.getConvertedDto(products);
			return ResponseEntity.ok(new ApiResponse("Found Success!", dto));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/{category}/all/products")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
		try {
			List<Product> products = productService.getProductsByCategory(category);
			if (products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No product found!", null));
			}
			List<ProductDto> dto = productService.getConvertedDto(products);
			return ResponseEntity.ok(new ApiResponse("Found Success!", dto));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductsByNameAndBrand(@RequestParam String productName,
			@RequestParam String brandName) {
		try {
			List<Product> products = productService.getProductsByNameAndBrand(productName, brandName);
			if (products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No products found!", null));
			}
			List<ProductDto> dto = productService.getConvertedDto(products);
			return ResponseEntity.ok(new ApiResponse("Found Success!", dto));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String brand,
			@RequestParam String category) {
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
			if (products.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No products found!", null));
			}
			List<ProductDto> dto = productService.getConvertedDto(products);
			return ResponseEntity.ok(new ApiResponse("Found Success!", dto));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,
			@RequestParam String name) {
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

}
