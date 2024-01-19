package com.reotra.demomonolith.commande.dto;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetCommandeListResponse(
        String numCommande,
        Double prixTotal,
        String produitID,
        Integer quantite,
        LocalDateTime dateCreation,
        StatutCommande statut,
        LocalDateTime dateLivraison,
        String adresseLivraison
) {

    public static GetCommandeListResponse from(Commande commande) {
        return GetCommandeListResponse.builder()
                .numCommande(commande.getNumCommande())
                .prixTotal(commande.getPrixTotal())
                .produitID(String.valueOf(commande.getProduit().getId()))
                .quantite(commande.getQuantite())
                .dateCreation(commande.getDateCreation())
                .dateLivraison(commande.getDateLivraison())
                .adresseLivraison(commande.getAdresseLivraison())
                .statut(commande.getStatut())
                .build();
    }

    public static List<GetCommandeListResponse> fromList(List<Commande> commandes) {
        return commandes.stream().map(GetCommandeListResponse::from).toList();
    }
}
