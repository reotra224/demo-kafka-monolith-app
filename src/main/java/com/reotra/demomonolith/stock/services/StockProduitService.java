package com.reotra.demomonolith.stock.services;

import com.reotra.demomonolith.stock.dto.StockProduitMisAJour;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.stock.domain.StockProduit;

import java.util.UUID;

public interface StockProduitService {

    GenericResponse<StockProduitMisAJour> mettreAJourLeStockDuProduit(UUID produitId, Integer quantite);

    StockProduit recupererLeStockPourUnProduit(UUID uuid);
}
