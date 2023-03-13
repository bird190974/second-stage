package com.bird.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class ClientFilter {
    String passportNo;
    String driverLicenceNo;
    LocalDate driverLicenceExpiration;
    BigDecimal creditAmount;
    Integer clientRating;
}
