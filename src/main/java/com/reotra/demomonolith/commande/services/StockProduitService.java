package com.reotra.demomonolith.commande.services;

import com.reotra.demomonolith.commande.dto.StockProduitMisAJour;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.stock.domain.StockProduit;

import java.util.UUID;

public interface StockProduitService {

    GenericResponse<StockProduitMisAJour> mettreAJourLeStockDuProduit(UUID produitId, Integer quantite);

    StockProduit recupererLeStockPourUnProduit(UUID uuid);
    boolean verifierLeStockPourUnProduit(UUID uuid, Integer quantite);
}
