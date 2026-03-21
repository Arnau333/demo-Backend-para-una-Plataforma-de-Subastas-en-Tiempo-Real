package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids")
@Getter @Setter
public class BidEntity {
    @Id
    @GeneratedValue
    private UUID id;
    
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String bidderName;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;
}
