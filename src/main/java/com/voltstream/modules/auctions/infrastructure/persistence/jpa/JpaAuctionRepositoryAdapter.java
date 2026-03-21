package com.voltstream.modules.auctions.infrastructure.persistence.jpa;

import com.voltstream.modules.auctions.domain.model.Auction;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaAuctionRepositoryAdapter implements AuctionRepository {

    private final SpringDataAuctionRepository springRepository;
    private final SpringDataBidRepository bidRepository;

    public JpaAuctionRepositoryAdapter(SpringDataAuctionRepository springRepository, SpringDataBidRepository bidRepository) {
        this.springRepository = springRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public void saveBid(UUID auctionId, java.math.BigDecimal amount, String bidderName) {
        AuctionEntity auctionEntity = springRepository.findById(auctionId)
            .orElseThrow(() -> new RuntimeException("Auction not found"));
        BidEntity bid = new BidEntity();
        bid.setAmount(amount);
        bid.setBidderName(bidderName);
        bid.setTimestamp(LocalDateTime.now());
        bid.setAuction(auctionEntity);
        bidRepository.save(bid);
    }

    @Override
    public Optional<Auction> findById(UUID id) {
        return springRepository.findById(id)
            .map(entity -> new Auction(entity.getId(), entity.getTitle(), entity.getCurrentPrice(), entity.getEndTime(), entity.isActive()));
    }

    @Override
    public void save(Auction auction) {
        AuctionEntity entity = springRepository.findById(auction.getId())
            .orElse(new AuctionEntity());
        entity.setId(auction.getId());
        entity.setTitle(auction.getTitle());
        entity.setCurrentPrice(auction.getCurrentPrice());
        entity.setEndTime(auction.getEndTime());
        entity.setActive(auction.isActive());
        springRepository.save(entity);
    }

    @Override
    public List<Auction> findAll() {
        return springRepository.findAll().stream()
            .map(entity -> new Auction(entity.getId(), entity.getTitle(), entity.getCurrentPrice(), entity.getEndTime(), entity.isActive()))
            .collect(Collectors.toList());
    }
}
