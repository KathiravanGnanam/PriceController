package com.blueyonder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blueyonder.model.Order;
import com.blueyonder.repository.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ProductService productService;
	
	public List<Order> getOrder() {
		return orderRepo.findAll();
	}

	public ResponseEntity deleteOrder(Long id) {
		Optional<Order> order=orderRepo.findById(id);
		
		if(order.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
		final String uri = "http://localhost:8080/api/product/updateQuantity"; // Assuming this is the correct endpoint
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, order.get());
		
		orderRepo.deleteById(id);
		return new ResponseEntity(HttpStatus.OK);
		
	}

	public Order addOrder(Order order) {
		return orderRepo.save(order);
	}

}
