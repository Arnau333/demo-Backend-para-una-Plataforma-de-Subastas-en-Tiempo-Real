package com.voltstream.modules.auctions.config;

import com.voltstream.modules.auctions.application.PlaceBidUseCase;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuctionConfig {
    @Bean
    public PlaceBidUseCase placeBidUseCase(AuctionRepository repository) {
        return new PlaceBidUseCase(repository);
    }
}

