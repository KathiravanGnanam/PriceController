package com.blueyonder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blueyonder.service.ProductService;

@Component
public class MyScheduler {

	@Autowired
	private ProductService productService;
	
    @Scheduled(initialDelay = 300000, fixedDelay = 300000)
    public void myMethod() {
    	System.out.println("Executed!......");
        productService.updateProductData();
    }
}

