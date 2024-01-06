package com.reotra.demomonolith.commande.dto;

import java.util.UUID;

public record CreerCommandeRequest(UUID produitID, Integer quantite, String adresse) {
}
