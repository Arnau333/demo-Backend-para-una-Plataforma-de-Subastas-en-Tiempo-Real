package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataBidRepository extends JpaRepository<BidEntity, UUID> {
}
