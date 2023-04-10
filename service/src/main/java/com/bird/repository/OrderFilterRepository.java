package com.bird.repository;

import com.bird.dto.OrderFilter;
import com.bird.entity.Order;

import java.util.List;

public interface OrderFilterRepository {

    List<Order> findByFilter(OrderFilter filter);
}
