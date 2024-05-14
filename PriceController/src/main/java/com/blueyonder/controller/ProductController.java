package com.blueyonder.controller;

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

import com.blueyonder.model.Order;
import com.blueyonder.model.Product;
import com.blueyonder.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/get")
	public ResponseEntity<List<Product>> getProduct(){
		return new ResponseEntity<>(productService.getProduct(),HttpStatus.OK);
	}
	
	
	@GetMapping("/getByRange")
	public ResponseEntity<List<Product>> getProductByRange(@RequestParam double min,@RequestParam double max){
		return new ResponseEntity<>(productService.getProductByRange(min,max),HttpStatus.OK);
	}
	
	@GetMapping("/getProductByAsce")
	public ResponseEntity<List<Product>> getProductByAsce(){
		return new ResponseEntity<>(productService.getProductByAsce(),HttpStatus.OK);
	}
	
	@GetMapping("/getProductByDesc")
	public ResponseEntity<List<Product>> getProductByDesc(){
		return new ResponseEntity<>(productService.getProductByDesc(),HttpStatus.OK);
	}
	
	@GetMapping("/getTopSellingProduct")
	public ResponseEntity<List<Product>> getTopSellingProduct(){
		return new ResponseEntity<>(productService.getTopSellingProduct(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		return new ResponseEntity<>(productService.addProduct(product),HttpStatus.OK);
	}
	
	@PutMapping("/updateQuantity")
	public ResponseEntity<List<Integer>> updateProductQuantity(@RequestBody Order order) {
		return productService.updateProductData(order);
	}
	
	@PutMapping("/updateQuantitybyAdd")
	public ResponseEntity<List<Integer>> updateProductQuantityByAdding(@RequestBody Order order) {
		return productService.updateProductDatabyAdd(order);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public void deleteProductById(@PathVariable Long id) {
		productService.deleteById(id);
	}

}
