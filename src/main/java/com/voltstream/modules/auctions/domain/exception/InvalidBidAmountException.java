package com.voltstream.modules.auctions.domain.exception;

public class InvalidBidAmountException extends RuntimeException {
    public InvalidBidAmountException(String message) {
        super(message);
    }
}