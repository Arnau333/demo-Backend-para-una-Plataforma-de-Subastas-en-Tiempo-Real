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

    public JpaAuctionRepositoryAdapter(SpringDataAuctionRepository springRepository) {
        this.springRepository = springRepository;
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
