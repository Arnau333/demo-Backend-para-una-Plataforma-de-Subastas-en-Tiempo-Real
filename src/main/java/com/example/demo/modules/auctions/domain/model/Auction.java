package com.voltstream.modules.auctions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Auction {
    private final UUID id;
    private final String title;
    private Double currentPrice;
    private final LocalDateTime endTime;
    private boolean active;

    // Constructor para crear una subasta nueva
    public Auction(UUID id, String title, Double initialPrice, LocalDateTime endTime) {
        this.id = id;
        this.title = title;
        this.currentPrice = initialPrice;
        this.endTime = endTime;
        this.active = true;
    }

    // REGLA DE NEGOCIO: Aquí es donde vive la "magia"
    public void executeBid(Double amount) {
        if (!active || LocalDateTime.now().isAfter(endTime)) {
            throw new RuntimeException("La subasta ha finalizado");
        }
        if (amount <= currentPrice) {
            throw new RuntimeException("La puja debe ser mayor al precio actual");
        }
        this.currentPrice = amount;
    }

    // Getters
    public UUID getId() { return id; }
    public Double getCurrentPrice() { return currentPrice; }
}
