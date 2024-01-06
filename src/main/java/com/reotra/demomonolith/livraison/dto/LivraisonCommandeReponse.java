package com.reotra.demomonolith.livraison.dto;

import com.reotra.demomonolith.livraison.domain.StatutLivraison;
import lombok.Builder;

@Builder
public record LivraisonCommandeReponse(String numeroCommande, String numeroLivraison, String adresseLivraison, StatutLivraison statut) {
}
