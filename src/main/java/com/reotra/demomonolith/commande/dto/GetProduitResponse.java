package com.reotra.demomonolith.commande.dto;

import com.reotra.demomonolith.commande.domain.Produit;
import lombok.Builder;

@Builder
public record GetProduitResponse(String id, String nom, Double prix) {
    public static GetProduitResponse creerDepuisUne(Produit produit) {
        return GetProduitResponse.builder()
                .id(produit.getId().toString())
                .nom(produit.getNom())
                .prix(produit.getPrix())
                .build();
    }
}
