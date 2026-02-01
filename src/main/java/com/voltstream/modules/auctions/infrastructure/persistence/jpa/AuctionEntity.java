package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "auctions")
@Getter @Setter
public class AuctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String title;
    
    @Column(precision = 19, scale = 2)
    private BigDecimal currentPrice;
    
    private LocalDateTime endTime;
    
    private boolean active;

    @Version
    private Long version;
}
