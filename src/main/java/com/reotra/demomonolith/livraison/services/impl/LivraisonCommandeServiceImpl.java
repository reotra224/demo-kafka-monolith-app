package com.reotra.demomonolith.livraison.services.impl;

import com.reotra.demomonolith.commande.services.CommandeService;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.common.services.UtilitairesService;
import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.livraison.domain.LivraisonCommande;
import com.reotra.demomonolith.livraison.domain.StatutLivraison;
import com.reotra.demomonolith.livraison.domain.SuiviLivraison;
import com.reotra.demomonolith.livraison.dto.ChangerEtatRequest;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeRequest;
import com.reotra.demomonolith.livraison.repositories.LivraisonCommandeRepository;
import com.reotra.demomonolith.livraison.repositories.SuiviLivraisonRepository;
import com.reotra.demomonolith.livraison.services.LivraisonCommandeService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Service
public class LivraisonCommandeServiceImpl implements LivraisonCommandeService {

    private final LivraisonCommandeRepository repository;
    private final SuiviLivraisonRepository suiviLivraisonRepository;
    private final UtilitairesService utilitairesService;
    private final CommandeService commandeService;

    public LivraisonCommandeServiceImpl(LivraisonCommandeRepository repository, SuiviLivraisonRepository suiviLivraisonRepository, UtilitairesService utilitairesService, @Lazy CommandeService commandeService) {
        this.repository = repository;
        this.suiviLivraisonRepository = suiviLivraisonRepository;
        this.utilitairesService = utilitairesService;
        this.commandeService = commandeService;
    }


    @Override
    @Transactional
    public GenericResponse<LivraisonCommandeReponse> informerCreationNouvelleCommande(LivraisonCommandeRequest livraisonCommandeRequest) {

        LivraisonCommande livraisonCommande = repository.save(LivraisonCommande.builder()
                .numeroLivraison(utilitairesService.genererUnNouveauNumero(CounterType.LIVRAISON))
                .statut(StatutLivraison.EN_VALIDATION)
                .commande(livraisonCommandeRequest.commande())
                .adresse(livraisonCommandeRequest.commande().getAdresseLivraison())
                .dateCreation(LocalDateTime.now())
                .build());

        suiviLivraisonRepository.save(SuiviLivraison.builder()
                .livraison(livraisonCommande)
                .nouveauStatut(livraisonCommande.getStatut())
                .dateChangement(LocalDateTime.now())
                .build());

        return GenericResponse.success(LivraisonCommandeReponse.builder()
                .numeroCommande(livraisonCommandeRequest.commande().getNumCommande())
                .numeroLivraison(livraisonCommande.getNumeroLivraison())
                .statut(livraisonCommande.getStatut())
                .adresseLivraison(livraisonCommande.getAdresse())
                .build());
    }

    @Override
    @Transactional
    public GenericResponse<LivraisonCommandeReponse> mettreAJourLetatDeLaLivraison(ChangerEtatRequest changerEtatRequest) {

        // Mettre à jour la livraison
        LivraisonCommande livraison = retrouverUneLivraison(changerEtatRequest.numeroLivraison());
        livraison.setStatut(changerEtatRequest.statut());
        livraison = repository.save(livraison);

        // Mettre à jour l'historique
        suiviLivraisonRepository.save(SuiviLivraison.builder()
                .livraison(livraison)
                .nouveauStatut(livraison.getStatut())
                .dateChangement(LocalDateTime.now())
                .build());


        LivraisonCommandeReponse livraisonEtat = LivraisonCommandeReponse.builder()
                .numeroCommande(livraison.getCommande().getNumCommande())
                .numeroLivraison(livraison.getNumeroLivraison())
                .statut(livraison.getStatut())
                .adresseLivraison(livraison.getAdresse())
                .build();

        // Informer le service de commande, quand la commande sera livré
        if (StatutLivraison.COMPLETE.equals(livraison.getStatut())) {
            commandeService.informerQueLaCommandeAeteLivre(livraisonEtat);
        }

        return GenericResponse.success(livraisonEtat);
    }

    @Override
    public LivraisonCommande retrouverUneLivraison(String numeroLivraison) {
        return repository.findById(numeroLivraison)
                .orElseThrow(() -> HttpClientErrorException.create("La livraison avec le numéro #" + numeroLivraison + " n'existe pas", HttpStatus.NOT_FOUND, "Livraison non trouvé", null, null, null));
    }
}
