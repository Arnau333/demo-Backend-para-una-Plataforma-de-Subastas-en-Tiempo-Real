package com.voltstream.modules.auctions.application;

import com.voltstream.modules.auctions.domain.exception.AuctionNotFoundException;
import com.voltstream.modules.auctions.domain.model.Auction;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PlaceBidUseCase {
    private final AuctionRepository repository;

    public PlaceBidUseCase(AuctionRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID auctionId, BigDecimal amount) {
        Auction auction = repository.findById(auctionId)
            .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        auction.executeBid(amount, LocalDateTime.now());

        repository.save(auction);
    }
}
