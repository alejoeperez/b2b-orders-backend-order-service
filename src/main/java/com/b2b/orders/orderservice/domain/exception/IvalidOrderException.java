package com.b2b.orders.orderservice.domain.exception;

public class IvalidOrderException extends RuntimeException{
    public IvalidOrderException(String message) {
        super(message);
    }
}
