package com.voltstream.modules.auctions.domain.model.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.voltstream.modules.auctions.domain.model.Auction;

public interface AuctionRepository {
    // Definimos qué necesitamos, pero NO cómo se hace (eso es Infrastructure)
    Optional<Auction> findById(UUID id);
    void save(Auction auction);
    void saveBid(UUID auctionId, BigDecimal amount, String bidderName);
    List<Auction> findAll();

    /**
     * Returns raw bid data for a given auction, ordered newest-first.
     * Each element is [id (UUID), amount (BigDecimal), bidderName (String), timestamp (LocalDateTime)]
     */
    List<Object[]> findBidsByAuctionId(UUID auctionId);
}
