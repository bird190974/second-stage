package com.bird.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ClientReadDto(
        Integer id,
        String passportNo,
        String driverLicenceNo,
        LocalDate driverLicenceExpiration,
        BigDecimal creditAmount,
        Integer clientRating) {
}

