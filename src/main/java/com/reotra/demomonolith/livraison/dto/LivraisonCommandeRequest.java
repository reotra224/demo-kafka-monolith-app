package com.reotra.demomonolith.livraison.dto;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import lombok.Builder;

@Builder
public record LivraisonCommandeRequest(
        String nomProduit,
        Double prix,
        Commande commande) {
}