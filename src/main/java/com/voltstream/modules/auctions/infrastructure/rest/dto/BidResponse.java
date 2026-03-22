package com.voltstream.modules.auctions.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BidResponse {
    private UUID id;
    private BigDecimal amount;
    private String bidderName;
    private LocalDateTime timestamp;

    public BidResponse(UUID id, BigDecimal amount, String bidderName, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.bidderName = bidderName;
        this.timestamp = timestamp;
    }

    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getBidderName() { return bidderName; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
