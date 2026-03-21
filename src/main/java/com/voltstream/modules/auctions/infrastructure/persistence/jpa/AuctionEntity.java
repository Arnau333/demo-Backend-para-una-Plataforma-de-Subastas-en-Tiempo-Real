package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "auctions")
@Getter @Setter
public class AuctionEntity {
    @Id
    private UUID id;
    
    private String title;
    
    @Column(precision = 19, scale = 2)
    private BigDecimal currentPrice;
    
    private LocalDateTime endTime;
    
    private boolean active;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<BidEntity> bids;

    @Version
    private Long version;
}
