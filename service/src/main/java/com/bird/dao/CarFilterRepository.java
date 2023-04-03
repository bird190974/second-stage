package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Car;

import java.util.List;

public interface CarFilterRepository {

    List<Car> findByFilter(CarFilter filter);
}
