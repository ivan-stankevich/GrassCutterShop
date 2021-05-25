package com.progect.GrassCutterShop.entity;

import com.progect.GrassCutterShop.entity.enums.Engine;
import com.progect.GrassCutterShop.entity.enums.Manufacturer;
import com.progect.GrassCutterShop.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product implements Entity {
    private Long productId;
    private String title;
    private Manufacturer manufacturer;
    private Type type;
    private Engine engine;
    private BigDecimal price;
}
