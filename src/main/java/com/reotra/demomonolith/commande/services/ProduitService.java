package com.reotra.demomonolith.commande.services;

import com.reotra.demomonolith.commande.domain.Produit;

import java.util.UUID;

public interface ProduitService {
    Produit trouverUnProduitAvecSonID(UUID uuid);
}
