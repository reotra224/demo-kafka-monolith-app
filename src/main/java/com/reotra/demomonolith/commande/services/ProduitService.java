package com.reotra.demomonolith.commande.services;

import com.reotra.demomonolith.commande.domain.Produit;
import com.reotra.demomonolith.stock.domain.StockProduit;

import java.util.UUID;

public interface ProduitService {
    Produit trouverUnProduitAvecSonID(UUID uuid);
}
