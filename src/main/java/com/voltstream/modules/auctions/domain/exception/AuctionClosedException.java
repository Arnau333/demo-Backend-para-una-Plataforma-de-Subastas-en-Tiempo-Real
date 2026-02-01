package com.voltstream.modules.auctions.domain.exception;

public class AuctionClosedException extends RuntimeException {
    public AuctionClosedException(String message) {
        super(message);
    }
}