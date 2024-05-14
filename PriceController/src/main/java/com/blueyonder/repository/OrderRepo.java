package com.blueyonder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blueyonder.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

	@Query(value="select * from orders where date_time>= NOW() - INTERVAL ':i minutes'",nativeQuery=true)
	List<Order> findAllDataByTime(int i);

	@Query(value="select * from orders where date_time>=:min and date_time<=:max",nativeQuery=true)
	List<Order> getOrderByRange(LocalDateTime min, LocalDateTime max);

	@Query(value="select * from orders where :id=any(p_id)",nativeQuery=true)
	List<Order> getOrderByProductId(Long id);

}
