package com.voltstream.modules.auctions.application;

import com.voltstream.modules.auctions.domain.model.Auction;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateAuctionUseCase {
    private final AuctionRepository repository;

    public CreateAuctionUseCase(AuctionRepository repository) {
        this.repository = repository;
    }

    public UUID execute(String title, BigDecimal startPrice, LocalDateTime endTime) {
        UUID id = UUID.randomUUID();
        Auction auction = new Auction(id, title, startPrice, endTime, true);
        repository.save(auction);
        return id;
    }
}
