package com.voltstream.modules.auctions.infrastructure.rest.dto;

import java.util.UUID;

public class CreateAuctionResponse {
    private UUID auctionId;

    public CreateAuctionResponse(UUID auctionId) {
        this.auctionId = auctionId;
    }

    public UUID getAuctionId() { return auctionId; }
    public void setAuctionId(UUID auctionId) { this.auctionId = auctionId; }
}
