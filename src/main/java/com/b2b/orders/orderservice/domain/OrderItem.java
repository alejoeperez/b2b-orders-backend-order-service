package com.b2b.orders.orderservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class OrderItem {

    private final String productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal taxPercent;
    private final BigDecimal discountPercent;

    private final BigDecimal lineNetAmount;
    private final BigDecimal lineTaxAmount;
    private final BigDecimal lineGrossAmount;

    private OrderItem(String productId, String productName,
                      int quantity, BigDecimal unitPrice, BigDecimal taxPercent,
                      BigDecimal discountPercent, BigDecimal lineNetAmount,
                      BigDecimal lineTaxAmount, BigDecimal lineGrossAmount) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.taxPercent = taxPercent;
        this.discountPercent = discountPercent;
        this.lineNetAmount = lineNetAmount;
        this.lineTaxAmount = lineTaxAmount;
        this.lineGrossAmount = lineGrossAmount;
    }

    public static OrderItem create(String productId,
                                   String productName,
                                   int quantity,
                                   BigDecimal unitPrice,
                                   BigDecimal taxPercent,
                                   BigDecimal discountPercent) {

        //validation of attributes based on business rules
        if(productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId cannot be null or blank");
        }
        if(productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("productName cannot be null or blank");
        }
        if(quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
        if(unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("unitPrice must be greater than 0");
        }
        if(taxPercent == null || taxPercent.compareTo(BigDecimal.ZERO) < 0
                || taxPercent.compareTo(BigDecimal.valueOf(100)) > 0 ) {
            throw new IllegalArgumentException("taxPercent must be between 0 and 100");
        }
        if(discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) < 0
                || discountPercent.compareTo(BigDecimal.valueOf(100)) > 0 ) {
            throw new IllegalArgumentException("discountPercent must be between 0 and 100");
        }

        BigDecimal quantityBd = BigDecimal.valueOf(quantity);

        //LineNetAmount = unitPrice * quantity * (1 - discountPercent/100)
        BigDecimal discountFactor = BigDecimal.ONE
                                    .subtract(discountPercent.divide(BigDecimal.valueOf(100))); //(1 - discountPercent/100)

        BigDecimal lineNetAmount = unitPrice.multiply(quantityBd)
                                            .multiply(discountFactor);

        //lineTaxAmount = netAmount * (taxPercent/100)
        BigDecimal taxFactor = taxPercent.divide(BigDecimal.valueOf(100));
        BigDecimal lineTaxAmount = lineNetAmount.multiply(taxFactor);

        //gross = netAmount + tax
        BigDecimal lineGrossAmount =  lineNetAmount.add(lineTaxAmount);

        return new OrderItem(
                productId,
                productName,
                quantity,
                unitPrice,
                taxPercent,
                discountPercent,
                lineNetAmount,
                lineTaxAmount,
                lineGrossAmount
        );

    }
}
