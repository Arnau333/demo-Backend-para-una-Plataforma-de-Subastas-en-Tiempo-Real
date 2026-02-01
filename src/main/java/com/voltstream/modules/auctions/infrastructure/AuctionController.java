package com.voltstream.modules.auctions.infrastructure;

import com.voltstream.modules.auctions.application.PlaceBidUseCase;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final PlaceBidUseCase placeBidUseCase;

    public AuctionController(PlaceBidUseCase placeBidUseCase) {
        this.placeBidUseCase = placeBidUseCase;
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
