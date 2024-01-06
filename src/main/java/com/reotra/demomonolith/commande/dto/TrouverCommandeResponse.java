package com.reotra.demomonolith.commande.dto;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TrouverCommandeResponse(
        String numCommande,
        Double prixTotal,
        GetProduitResponse produit,
        Integer quantite,
        LocalDateTime dateCreation,
        LocalDateTime dateLivraison,
        StatutCommande statut,
        List<HistoriqueCommandeReponse> historiqueCommande
) {

    public static TrouverCommandeResponse creerDepuisUne(Commande commande) {
        return TrouverCommandeResponse.builder()
                .numCommande(commande.getNumCommande())
                .prixTotal(commande.getPrixTotal())
                .produit(GetProduitResponse.creerDepuisUne(commande.getProduit()))
                .quantite(commande.getQuantite())
                .dateCreation(commande.getDateCreation())
                .statut(commande.getStatut())
                .historiqueCommande(HistoriqueCommandeReponse.creerDepuisUne(commande.getEvolutions()))
                .build();
    }
}
