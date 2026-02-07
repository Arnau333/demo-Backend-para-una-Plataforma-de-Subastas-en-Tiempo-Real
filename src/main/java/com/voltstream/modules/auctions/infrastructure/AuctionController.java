package com.voltstream.modules.auctions.infrastructure;

import com.voltstream.modules.auctions.application.CreateAuctionUseCase;
import com.voltstream.modules.auctions.application.PlaceBidUseCase;
import com.voltstream.modules.auctions.domain.model.Auction;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;
import com.voltstream.modules.auctions.infrastructure.rest.dto.CreateAuctionRequest;
import com.voltstream.modules.auctions.infrastructure.rest.dto.CreateAuctionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final CreateAuctionUseCase createAuctionUseCase;
    private final PlaceBidUseCase placeBidUseCase;
    private final AuctionRepository auctionRepository;

    public AuctionController(CreateAuctionUseCase createAuctionUseCase, PlaceBidUseCase placeBidUseCase, AuctionRepository auctionRepository) {
        this.createAuctionUseCase = createAuctionUseCase;
        this.placeBidUseCase = placeBidUseCase;
        this.auctionRepository = auctionRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAuctionResponse createAuction(@RequestBody CreateAuctionRequest request) {
        try {
            LocalDateTime endTime = LocalDateTime.now().plusHours(request.getDurationHours() != null ? request.getDurationHours() : 24);
            UUID auctionId = createAuctionUseCase.execute(request.getTitle(), request.getStartPrice(), endTime);
            return new CreateAuctionResponse(auctionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @PostMapping("/{id}/bids")
    public String placeBid(@PathVariable UUID id, @RequestParam Double amount) {
        try {
            placeBidUseCase.execute(id, BigDecimal.valueOf(amount));
            return "Puja realizada con éxito";
        } catch (RuntimeException e) {
            return "Error al pujar: " + e.getMessage();
        }
    }
}
