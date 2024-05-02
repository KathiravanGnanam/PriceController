package com.blueyonder.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		order.setDate_time(LocalDateTime.now());
		final String uri = "http://localhost:8080/api/product/updateQuantity";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, order);
		
		double price=0.0;
		for(int i=0;i<order.getP_id().size();i++) {
			price+=productService.getPrice(order.getP_id().get(i))*order.getP_qty().get(i);
		}
		order.setTotal_price(price);
		return orderRepo.save(order);
	}

}
