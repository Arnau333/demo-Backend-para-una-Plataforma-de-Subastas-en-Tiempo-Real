package com.voltstream.modules.auctions.domain.model;

import com.voltstream.modules.auctions.domain.exception.AuctionClosedException;
import com.voltstream.modules.auctions.domain.exception.InvalidBidAmountException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Auction {
    private final UUID id;
    private final String title;
    private BigDecimal currentPrice;
    private final LocalDateTime endTime;
    private boolean active;

    public Auction(UUID id, String title, BigDecimal currentPrice, LocalDateTime endTime, boolean active) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.currentPrice = Objects.requireNonNull(currentPrice, "Current price cannot be null");
        this.endTime = Objects.requireNonNull(endTime, "End time cannot be null");
        this.active = active;
    }

    public void executeBid(BigDecimal amount, LocalDateTime currentTime) {
        Objects.requireNonNull(amount, "Bid amount cannot be null");
        Objects.requireNonNull(currentTime, "Current time cannot be null");
        
        if (!active || currentTime.isAfter(endTime)) {
            throw new AuctionClosedException("Auction has ended");
        }
        if (amount.compareTo(currentPrice) <= 0) {
            throw new InvalidBidAmountException("Bid must be higher than current price");
        }
        this.currentPrice = amount;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isActive() { return active; }
}
