package com.reotra.demomonolith.commande.services.impl;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.Produit;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import com.reotra.demomonolith.commande.dto.CreerCommandeRequest;
import com.reotra.demomonolith.commande.dto.CreerCommandeResponse;
import com.reotra.demomonolith.commande.dto.StockProduitMisAJour;
import com.reotra.demomonolith.commande.dto.TrouverCommandeResponse;
import com.reotra.demomonolith.commande.repositories.CommandeRepository;
import com.reotra.demomonolith.commande.services.CommandeService;
import com.reotra.demomonolith.commande.services.ProduitService;
import com.reotra.demomonolith.commande.services.StockProduitService;
import com.reotra.demomonolith.common.GenericResponse;
import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.common.services.UCounterService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitService produitService;
    private final StockProduitService stockProduitService;
    private final UCounterService counterService;

    public CommandeServiceImpl(CommandeRepository commandeRepository, ProduitService produitService, StockProduitService stockProduitService, UCounterService counterService) {
        this.commandeRepository = commandeRepository;
        this.produitService = produitService;
        this.stockProduitService = stockProduitService;
        this.counterService = counterService;
    }

    @Override
    @Transactional
    public GenericResponse<CreerCommandeResponse> creerUneCommandeEnValidation(CreerCommandeRequest creerCommandeDTO) {

        Produit produitTrouve = produitService.trouverUnProduitAvecSonID(creerCommandeDTO.produitID());

        GenericResponse<StockProduitMisAJour> stockProduitMisAJour = stockProduitService.mettreAJourLeStockDuProduit(produitTrouve.getId(), creerCommandeDTO.quantite());

        return (!stockProduitMisAJour.success()) ? GenericResponse.error(stockProduitMisAJour.message()) : GenericResponse.success(CreerCommandeResponse.from(
                commandeRepository.save(
                        Commande.builder()
                                .numCommande("CMDE-" + counterService.genererProchaineValeur(CounterType.COMMANDE.name()))
                                .produit(produitTrouve)
                                .quantite(creerCommandeDTO.quantite())
                                .prixTotal(creerCommandeDTO.quantite()*produitTrouve.getPrix())
                                .statut(StatutCommande.EN_ATTENTE_DE_VALIDATION)
                                .dateCreation(LocalDateTime.now())
                                .build()
                ))
        );

        // Create evolution commande

        // Call livraison service to create livraison and suivi
    }

    @Override
    public CreerCommandeResponse validerUneCommande(String commandeID) {
        return null;
    }

    @Override
    public List<TrouverCommandeResponse> recupererListeDesCommandesPourUnProduit(String produitID) {
        return null;
    }
}
