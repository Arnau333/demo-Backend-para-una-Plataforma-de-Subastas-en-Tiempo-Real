package com.voltstream.modules.auctions.infrastructure.rest.dto;

import java.math.BigDecimal;

public class CreateAuctionRequest {
    private String title;
    private BigDecimal startPrice;
    private Integer durationHours;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public BigDecimal getStartPrice() { return startPrice; }
    public void setStartPrice(BigDecimal startPrice) { this.startPrice = startPrice; }
    
    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }
}
