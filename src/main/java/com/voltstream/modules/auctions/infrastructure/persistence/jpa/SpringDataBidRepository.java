package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataBidRepository extends JpaRepository<BidEntity, UUID> {
    List<BidEntity> findByAuctionIdOrderByTimestampDesc(UUID auctionId);
}
