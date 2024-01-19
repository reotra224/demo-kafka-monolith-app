package com.reotra.demomonolith.commande.services.impl;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.domain.EvolutionCommande;
import com.reotra.demomonolith.commande.domain.Produit;
import com.reotra.demomonolith.commande.domain.StatutCommande;
import com.reotra.demomonolith.commande.dto.CreerCommandeRequest;
import com.reotra.demomonolith.commande.dto.CreerCommandeResponse;
import com.reotra.demomonolith.commande.dto.GetCommandeListResponse;
import com.reotra.demomonolith.commande.repositories.EvolutionCommandeRepository;
import com.reotra.demomonolith.commande.services.ProduitService;
import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.common.services.UtilitairesService;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;
import com.reotra.demomonolith.commande.repositories.CommandeRepository;
import com.reotra.demomonolith.commande.services.CommandeService;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeRequest;
import com.reotra.demomonolith.livraison.services.LivraisonCommandeService;
import com.reotra.demomonolith.stock.dto.StockProduitMisAJour;
import com.reotra.demomonolith.stock.services.StockProduitService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitService produitService;
    private final StockProduitService stockProduitService;
    private final UtilitairesService utilitairesService;
    private final EvolutionCommandeRepository evolutionCommandeRepository;
    private final LivraisonCommandeService livraisonCommandeService;

    @Transactional
    @Override
    public GenericResponse<CreerCommandeResponse> creerUneCommandeEnValidation(CreerCommandeRequest creerCommandeDTO) {

        // Trouver le produit concerné
        Produit produitTrouve = produitService.trouverUnProduitAvecSonID(creerCommandeDTO.produitID());


        // Vérifier et mettre à jour le stock
        GenericResponse<StockProduitMisAJour> stockProduitMisAJour =  stockProduitService.mettreAJourLeStockDuProduit(produitTrouve.getId(), creerCommandeDTO.quantite());
        if (!stockProduitMisAJour.success()) {
            return GenericResponse.error(stockProduitMisAJour.message());
        }

        // Création de la nouvelle commande
        Commande commandeCree = commandeRepository.save(
                Commande.builder()
                        .numCommande(utilitairesService.genererUnNouveauNumero(CounterType.COMMANDE))
                        .produit(produitTrouve)
                        .quantite(creerCommandeDTO.quantite())
                        .prixTotal(creerCommandeDTO.quantite() * produitTrouve.getPrix())
                        .statut(StatutCommande.EN_ATTENTE_DE_VALIDATION)
                        .dateCreation(LocalDateTime.now())
                        .adresseLivraison(creerCommandeDTO.adresse())
                        .build()
        );

        // Mettre à jour l'historique de la commande
        evolutionCommandeRepository.save(EvolutionCommande.builder()
                .commande(commandeCree)
                .nouveauStatut(commandeCree.getStatut())
                .dateChangement(LocalDateTime.now())
                .build());

        return GenericResponse.success(CreerCommandeResponse.from(commandeCree));
    }

    @Transactional
    @Override
    public GenericResponse<CreerCommandeResponse> validerUneCommande(String commandeID) {

        // Mettre à jour la commande
        Commande commande = rechercherUneCommande(commandeID);
        StatutCommande ancienStatut = commande.getStatut();

        commande.setStatut(StatutCommande.VALIDE);
        commande = commandeRepository.save(commande);

        // Mettre à jour l'évolution de la commande
        evolutionCommandeRepository.save(EvolutionCommande.builder()
                .commande(commande)
                .ancienStatut(ancienStatut)
                .nouveauStatut(commande.getStatut())
                .dateChangement(LocalDateTime.now())
                .build());

        //Informer le service de livraison de la création d'une nouvelle commande
        livraisonCommandeService.informerCreationNouvelleCommande(LivraisonCommandeRequest.builder()
                .nomProduit(commande.getProduit().getNom())
                .prix(commande.getPrixTotal())
                .commande(commande)
                .build());

        return GenericResponse.success(CreerCommandeResponse.from(commande));
    }


    @Override
    public void informerQueLaCommandeAeteLivre(LivraisonCommandeReponse livraisonEtat) {

        Commande commande = rechercherUneCommande(livraisonEtat.numeroCommande());

        // Mettre à jour la commande
        StatutCommande ancienStatut = commande.getStatut();
        commande.setStatut(StatutCommande.COMPLETE);
        commande = commandeRepository.save(commande);

        // Mettre à jour l'évolution de la commande
        evolutionCommandeRepository.save(EvolutionCommande.builder()
                .commande(commande)
                .ancienStatut(ancienStatut)
                .nouveauStatut(commande.getStatut())
                .dateChangement(LocalDateTime.now())
                .build());

        //On pourrait à ce niveau, notifier le client ou un autre service si nécessaire de la livraison de la commande
    }

    @Override
    public List<GetCommandeListResponse> recupererTouteLesCommandes() {
        return GetCommandeListResponse.fromList(commandeRepository.findAll());
    }


    @Override
    public Commande rechercherUneCommande(String numeroCommande) {
        return commandeRepository.findById(numeroCommande)
                .orElseThrow(() -> HttpClientErrorException.create("La commande numéro #" + numeroCommande + " n'existe pas", HttpStatus.NOT_FOUND, "Commande non trouvé", null, null, null));
    }
}
