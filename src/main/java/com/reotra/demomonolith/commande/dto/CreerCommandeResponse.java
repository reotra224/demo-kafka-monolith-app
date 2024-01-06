package com.reotra.demomonolith.commande.dto;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreerCommandeResponse(
        String numCommande,
        Double prixTotal,
        String produitID,
        Integer quantite,
        LocalDateTime dateCreation,
        StatutCommande statut) {

    public static CreerCommandeResponse from(Commande commande) {
        return CreerCommandeResponse.builder()
                .numCommande(commande.getNumCommande())
                .prixTotal(commande.getPrixTotal())
                .produitID(String.valueOf(commande.getProduit().getId()))
                .quantite(commande.getQuantite())
                .dateCreation(commande.getDateCreation())
                .statut(commande.getStatut())
                .build();
    }
}
