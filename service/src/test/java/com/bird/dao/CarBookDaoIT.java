package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import com.bird.entity.Order;
import com.bird.integration.TestBase;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarBookDaoIT extends TestBase {
    private final CarBookDao carBookDao = CarBookDao.getInstance();

    @Test
    void findAllByFilterQueryDsl() {
        CarFilter filter = CarFilter.builder()
                .engine(Engine.DIESEL)
                .gearbox(Gearbox.ROBOT)
                .seatsQuantity(7)
                .build();
        List<Order> result = carBookDao.findAllByFilterQueryDsl(session, filter);
        assertThat(result).hasSize(1);
    }

    @Test
    void findAllByFilterCriteria() {
        CarFilter filter = CarFilter.builder()
                .engine(Engine.DIESEL)
                .gearbox(Gearbox.ROBOT)
                .seatsQuantity(7)
                .build();
        List<Order> result = carBookDao.findAllByFilterQueryDsl(session, filter);
        assertThat(result).hasSize(1);
    }
}
