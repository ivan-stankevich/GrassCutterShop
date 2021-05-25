package com.progect.GrassCutterShop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order implements Entity{
    private Long orderId;
    private Long userId;
    private Long productId;
    private String productTitle;
    private LocalDateTime orderTime;
    private BigDecimal orderCost;
}
