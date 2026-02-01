package com.example.voltstream.modules.auctions.domain.model.repository;

import java.util.Optional;
import java.util.UUID;

import com.voltstream.modules.auctions.domain.model.Auction;

public interface AuctionRepository {
    // Definimos qué necesitamos, pero NO cómo se hace (eso es Infrastructure)
    Optional<Auction> findById(UUID id);
    void save(Auction auction);
}
