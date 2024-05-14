package com.blueyonder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blueyonder.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>  {

	@Modifying
    @Transactional
//	@Query(value="update product set price=price+price*20/100, date_time = CURRENT_TIMESTAMP",nativeQuery=true)
	@Query(value="update product set\n" +
			"price = CASE\n" +
			"WHEN sold_qty<:threshold THEN price-price*5/100\n" +
			"ELSE price\n" +
			"END,\n" +
			"last_updated_date_time = CASE\n" +
			"WHEN sold_qty<:threshold THEN CURRENT_TIMESTAMP\n" +
			"ELSE last_updated_date_time\n" +
			"END,\n" +
			"last_checked_date_time = CURRENT_TIMESTAMP,\n" +
			"sold_qty = 0",nativeQuery=true)
	void modifyProductData(Long threshold);

	@Modifying
    @Transactional
	@Query(value="update product set sold_qty=sold_qty-:p_qty, total_qty=total_qty+:p_qty where p_id=:p_id",nativeQuery=true)
	int modifyProductQuantity(Long p_id, Long p_qty);

	@Modifying
    @Transactional
	@Query(value="update product set sold_qty=sold_qty+:p_qty, total_qty=total_qty-:p_qty where p_id=:p_id and (select total_qty from product where p_id=:p_id)>=:p_qty ",nativeQuery=true)
	int modifyProductQuantitybyAdding(Long p_id, Long p_qty);

	@Modifying
    @Transactional
	@Query(value="select * from product where price>=:min and price<=:max",nativeQuery=true)
	List<Product> getProductByRange(double min, double max);

	@Modifying
    @Transactional
	@Query(value="select * from product order by price",nativeQuery=true)
	List<Product> getProductByAsce();

	@Modifying
    @Transactional
	@Query(value="select * from product order by price desc",nativeQuery=true)
	List<Product> getProductByDesc();

	@Query(value="SELECT * FROM (SELECT *,PERCENT_RANK() OVER (ORDER BY sold_qty DESC) AS percentile_rank FROM product) AS ranked_products WHERE percentile_rank <= 0.2 and sold_qty!=0",nativeQuery=true)
	List<Product> getTopSellingProduct();

	
	


}
