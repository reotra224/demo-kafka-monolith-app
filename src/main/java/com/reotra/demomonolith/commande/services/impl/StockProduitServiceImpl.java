package com.reotra.demomonolith.commande.services.impl;

import com.reotra.demomonolith.commande.dto.StockProduitMisAJour;
import com.reotra.demomonolith.commande.services.StockProduitService;
import com.reotra.demomonolith.common.GenericResponse;
import com.reotra.demomonolith.stock.domain.MouvementStock;
import com.reotra.demomonolith.stock.domain.StockProduit;
import com.reotra.demomonolith.stock.repositories.MouvementStockRepository;
import com.reotra.demomonolith.stock.repositories.StockProduitRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StockProduitServiceImpl implements StockProduitService {

    private final StockProduitRepository stockProduitRepository;
    private final MouvementStockRepository mouvementStockRepository;

    public StockProduitServiceImpl(StockProduitRepository stockProduitRepository, MouvementStockRepository mouvementStockRepository) {
        this.stockProduitRepository = stockProduitRepository;
        this.mouvementStockRepository = mouvementStockRepository;
    }

    @Transactional
    @Override
    public GenericResponse<StockProduitMisAJour> mettreAJourLeStockDuProduit(UUID produitId, Integer quantite) {

        StockProduit stockProduit = recupererLeStockPourUnProduit(produitId);
        if (stockProduit.getStockActuel() < quantite) {
            return GenericResponse.error("Le stock du produit #" + produitId + " est insuffisant");
        }

        Integer ancienStock = stockProduit.getStockActuel();
        Integer nouveauStock = stockProduit.getStockActuel() - quantite;

        stockProduit.setStockActuel(nouveauStock);
        stockProduitRepository.save(stockProduit);

        mouvementStockRepository.save(MouvementStock.builder()
                .ancienStock(ancienStock)
                .nouveauStock(nouveauStock)
                .dateMouvement(LocalDateTime.now())
                .stockProduit(stockProduit)
                .build());

        return GenericResponse.success(StockProduitMisAJour.builder().stockActuel(nouveauStock).produitID(produitId).ancienStock(ancienStock).build());
    }

    @Override
    public StockProduit recupererLeStockPourUnProduit(UUID uuid) {
        return stockProduitRepository.findByProduitId(uuid)
                .orElseThrow(() -> HttpClientErrorException.create("Le produit avec ID #" + uuid + " n'a pas de stock", HttpStatus.NOT_FOUND, "Stock Produit non trouvé", null, null, null));
    }

    @Override
    public boolean verifierLeStockPourUnProduit(UUID uuid, Integer quantite) {
        return recupererLeStockPourUnProduit(uuid).getStockActuel() >= quantite;
    }
}
