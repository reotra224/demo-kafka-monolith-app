package com.reotra.demomonolith.commande.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StockProduitMisAJour(UUID produitID, Integer stockActuel, Integer ancienStock) {
}
