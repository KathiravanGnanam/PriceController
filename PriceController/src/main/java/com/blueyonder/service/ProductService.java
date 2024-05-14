package com.blueyonder.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blueyonder.model.Order;
import com.blueyonder.model.Product;
import com.blueyonder.repository.ProductRepo;

@Service
public class ProductService {

	
  @Autowired
  private ProductRepo repo;

  private Long threshold = 10L;

  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
  
  public List<Product> getProduct() {
    return repo.findAll();
  }

  public Product addProduct(Product product) {
    product.setLast_updated_date_time(LocalDateTime.now());
    return repo.save(product);
  }

  public void updateProductData() {	  
    repo.modifyProductData(threshold);
    logger.info("Executed : "+LocalDateTime.now());
	  List<Product> product=repo.findAll();
	  for(int i=0;i<product.size();i++) {
		  logger.info("SOLD QTY : "+product.get(i).getSold_qty()+" Last Checked : "+product.get(i).getLast_checked_date_time()+" Last Updated : "+product.get(i).getLast_updated_date_time());
	  }
  }

  public int modify(Long p_id, Long p_qty) {
    return repo.modifyProductQuantity(p_id, p_qty);
  }
  
  public int modifybyAdding(Long p_id, Long p_qty) {
	    return repo.modifyProductQuantitybyAdding(p_id, p_qty);
	  }

  public ResponseEntity<List<Integer>> updateProductData(Order order) {
	  
	  	List<Integer> updatedId=new ArrayList<>();
	    for (int i = 0; i < order.getP_id().size(); i++) {
	      updatedId.add(modify(order.getP_id().get(i), order.getP_qty().get(i)));
	    }
	    return new ResponseEntity<>(updatedId,HttpStatus.OK);
  }

	public double getPrice(Long p_id) {
		Optional<Product> product=repo.findById(p_id);
		return product.get().getPrice();
	}

	public ResponseEntity<List<Integer>> updateProductDatabyAdd(Order order) {
		List<Integer> updatedId=new ArrayList<>();
	    for (int i = 0; i < order.getP_id().size(); i++) {
	      updatedId.add(modifybyAdding(order.getP_id().get(i), order.getP_qty().get(i)));
	    }
	    return new ResponseEntity<>(updatedId,HttpStatus.ACCEPTED);
	}

	public int getProductQty(Long p_id) {
		return repo.findById(p_id).get().getTotal_qty();
	}

	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	public List<Product> getProductByRange(double min, double max) {
		return repo.getProductByRange(min,max);
	}

	public List<Product> getProductByAsce() {
		return repo.getProductByAsce();
	}

	public List<Product> getProductByDesc() {
		return repo.getProductByDesc();
	}

	public List<Product> getTopSellingProduct() {
		return repo.getTopSellingProduct();
	}

}
