package com.reotra.demomonolith.commande.dto;

import com.reotra.demomonolith.commande.domain.EvolutionCommande;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Builder
public record HistoriqueCommandeReponse(String ancienStatut, String nouveauStatut, LocalDateTime dateChangement) {
    public static List<HistoriqueCommandeReponse> creerDepuisUne(@NonNull Collection<EvolutionCommande> evolutions) {
        return evolutions.stream().map(
                evolutionCommande -> HistoriqueCommandeReponse.builder()
                        .ancienStatut(evolutionCommande.getAncienStatut().name())
                        .nouveauStatut(evolutionCommande.getNouveauStatut().name())
                        .dateChangement(evolutionCommande.getDateChangement())
                        .build()
        ).toList();
    }
}
