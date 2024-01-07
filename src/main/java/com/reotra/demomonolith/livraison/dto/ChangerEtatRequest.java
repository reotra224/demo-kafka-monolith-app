package com.reotra.demomonolith.livraison.dto;

import com.reotra.demomonolith.livraison.domain.StatutLivraison;

public record ChangerEtatRequest(String numeroLivraison, StatutLivraison statut) {
}
