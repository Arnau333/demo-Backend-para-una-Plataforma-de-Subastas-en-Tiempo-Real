package com.voltstream.modules.auctions.infrastructure;

import com.voltstream.modules.auctions.application.CreateAuctionUseCase;
import com.voltstream.modules.auctions.application.PlaceBidUseCase;
import com.voltstream.modules.auctions.domain.model.Auction;
import com.voltstream.modules.auctions.domain.model.repository.AuctionRepository;
import com.voltstream.modules.auctions.domain.exception.AuctionNotFoundException;
import com.voltstream.modules.auctions.infrastructure.rest.dto.BidResponse;
import com.voltstream.modules.auctions.infrastructure.rest.dto.CreateAuctionRequest;
import com.voltstream.modules.auctions.infrastructure.rest.dto.CreateAuctionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final CreateAuctionUseCase createAuctionUseCase;
    private final PlaceBidUseCase placeBidUseCase;
    private final AuctionRepository auctionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AuctionController(CreateAuctionUseCase createAuctionUseCase, PlaceBidUseCase placeBidUseCase, AuctionRepository auctionRepository, SimpMessagingTemplate messagingTemplate) {
        this.createAuctionUseCase = createAuctionUseCase;
        this.placeBidUseCase = placeBidUseCase;
        this.auctionRepository = auctionRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAuctionResponse createAuction(@RequestBody CreateAuctionRequest request) {
        try {
            LocalDateTime endTime = LocalDateTime.now().plusHours(request.getDurationHours() != null ? request.getDurationHours() : 24);
            UUID auctionId = createAuctionUseCase.execute(request.getTitle(), request.getStartPrice(), endTime);

            // Broadcast new auction event via WebSocket
            messagingTemplate.convertAndSend("/topic/auctions", "New auction created: " + request.getTitle());

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

    @GetMapping("/{id}")
    public Auction getAuctionById(@PathVariable UUID id) {
        return auctionRepository.findById(id).orElseThrow(() -> new AuctionNotFoundException("Subasta no encontrada"));
    }

    @GetMapping("/{id}/bids")
    public List<BidResponse> getBidsByAuctionId(@PathVariable UUID id) {
        return auctionRepository.findBidsByAuctionId(id)
            .stream()
            .map(row -> new BidResponse(
                (UUID) row[0],
                (BigDecimal) row[1],
                (String) row[2],
                (LocalDateTime) row[3]
            ))
            .collect(Collectors.toList());
    }

    @PostMapping("/{id}/bids")
    public String placeBid(
            @PathVariable UUID id,
            @RequestParam Double amount,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String bidderName = userDetails != null ? userDetails.getUsername() : "Anónimo";
            placeBidUseCase.execute(id, BigDecimal.valueOf(amount), bidderName);

            // Broadcast bid update via WebSocket
            messagingTemplate.convertAndSend("/topic/auctions/" + id, "New bid placed: " + amount + " by " + bidderName);

            return "Puja realizada con éxito";
        } catch (RuntimeException e) {
            return "Error al pujar: " + e.getMessage();
        }
    }
}

