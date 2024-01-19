package com.reotra.demomonolith.stock.services.impl;

import com.reotra.demomonolith.stock.domain.MouvementStock;
import com.reotra.demomonolith.stock.dto.StockProduitMisAJour;
import com.reotra.demomonolith.stock.repositories.MouvementStockRepository;
import com.reotra.demomonolith.stock.services.StockProduitService;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.stock.domain.StockProduit;
import com.reotra.demomonolith.stock.repositories.StockProduitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StockProduitServiceImpl implements StockProduitService {

    private final StockProduitRepository stockProduitRepository;
    private final MouvementStockRepository mouvementStockRepository;

    @Transactional
    @Override
    public GenericResponse<StockProduitMisAJour> mettreAJourLeStockDuProduit(UUID produitId, Integer quantite) {

        // Vérifier le stock
        StockProduit stockProduit = recupererLeStockPourUnProduit(produitId);
        if (stockProduit.getStockActuel() < quantite) {
            return GenericResponse.error("Le stock du produit #" + produitId + " est insuffisant");
        }

        // Mettre à jour le stock
        Integer ancienStock = stockProduit.getStockActuel();
        Integer nouveauStock = stockProduit.getStockActuel() - quantite;
        stockProduit.setStockActuel(nouveauStock);
        stockProduitRepository.save(stockProduit);

        // Mettre à jour l'historique du stock
        mouvementStockRepository.save(
                MouvementStock.builder()
                        .ancienStock(ancienStock)
                        .nouveauStock(stockProduit.getStockActuel())
                        .dateMouvement(LocalDateTime.now())
                        .stockProduit(stockProduit)
                        .build()
        );

        return GenericResponse.success(StockProduitMisAJour.builder()
                .produitID(stockProduit.getProduit().getId())
                .stockActuel(stockProduit.getStockActuel())
                .ancienStock(ancienStock)
                .build());
    }

    @Override
    public StockProduit recupererLeStockPourUnProduit(UUID uuid) {
        return stockProduitRepository.findByProduitId(uuid)
                .orElseThrow(() -> HttpClientErrorException.create("Le produit avec ID #" + uuid + " n'a pas de stock", HttpStatus.NOT_FOUND, "Stock Produit non trouvé", null, null, null));
    }

}
