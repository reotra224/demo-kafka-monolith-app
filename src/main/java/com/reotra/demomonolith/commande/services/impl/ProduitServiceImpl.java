package com.reotra.demomonolith.commande.services.impl;

import com.reotra.demomonolith.commande.domain.Produit;
import com.reotra.demomonolith.commande.repositories.ProduitRepository;
import com.reotra.demomonolith.commande.services.ProduitService;
import com.reotra.demomonolith.common.GenericResponse;
import com.reotra.demomonolith.stock.domain.StockProduit;
import com.reotra.demomonolith.stock.repositories.StockProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit trouverUnProduitAvecSonID(UUID uuid) {
        return produitRepository.findById(uuid)
                .orElseThrow(() -> HttpClientErrorException.create("Le produit avec ID #" + uuid + " n'existe pas", HttpStatus.NOT_FOUND, "Produit non trouvé", null, null, null));
    }

}
