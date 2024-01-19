package com.reotra.demomonolith.livraison.services.impl;

import com.reotra.demomonolith.commande.services.CommandeService;
import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.common.services.UtilitairesService;
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
    private final UtilitairesService utilitairesService;
    private final SuiviLivraisonRepository suiviLivraisonRepository;
    private final CommandeService commandeService;

    public LivraisonCommandeServiceImpl(LivraisonCommandeRepository repository, UtilitairesService utilitairesService, SuiviLivraisonRepository suiviLivraisonRepository, @Lazy CommandeService commandeService) {
        this.repository = repository;
        this.utilitairesService = utilitairesService;
        this.suiviLivraisonRepository = suiviLivraisonRepository;
        this.commandeService = commandeService;
    }


    @Override
    @Transactional
    public GenericResponse<LivraisonCommandeReponse> informerCreationNouvelleCommande(LivraisonCommandeRequest livraisonCommandeRequest) {

        // Créer une nouvelle livraison
        LivraisonCommande nouvelleLivraison = repository.save(
                LivraisonCommande.builder()
                        .numeroLivraison(utilitairesService.genererUnNouveauNumero(CounterType.LIVRAISON))
                        .statut(StatutLivraison.EN_VALIDATION)
                        .commande(livraisonCommandeRequest.commande())
                        .adresse(livraisonCommandeRequest.commande().getAdresseLivraison())
                        .dateCreation(LocalDateTime.now())
                        .build()
        );


        // Mettre à jour l'historique de suivi d'une livraison
        suiviLivraisonRepository.save(SuiviLivraison.builder()
                .livraison(nouvelleLivraison)
                .nouveauStatut(nouvelleLivraison.getStatut())
                .dateChangement(LocalDateTime.now())
                .build()
        );

        return GenericResponse.success(LivraisonCommandeReponse.builder()
                .numeroCommande(livraisonCommandeRequest.commande().getNumCommande())
                .numeroLivraison(nouvelleLivraison.getNumeroLivraison())
                .statut(nouvelleLivraison.getStatut())
                .adresseLivraison(nouvelleLivraison.getAdresse())
                .build());
    }

    @Override
    @Transactional
    public GenericResponse<LivraisonCommandeReponse> mettreAJourLetatDeLaLivraison(ChangerEtatRequest changerEtatRequest) {

        // Mettre à jour la livraison
        LivraisonCommande livraisonCommande = retrouverUneLivraison(changerEtatRequest.numeroLivraison());
        StatutLivraison ancienStatut = livraisonCommande.getStatut();

        livraisonCommande.setStatut(changerEtatRequest.statut());
        repository.save(livraisonCommande);

        // Mettre à jour l'historique
        suiviLivraisonRepository.save(SuiviLivraison.builder()
                .livraison(livraisonCommande)
                .ancienStatut(ancienStatut)
                .nouveauStatut(livraisonCommande.getStatut())
                .dateChangement(LocalDateTime.now())
                .build()
        );

        LivraisonCommandeReponse livraisonReponse = LivraisonCommandeReponse.builder()
                .numeroCommande(livraisonCommande.getCommande().getNumCommande())
                .numeroLivraison(livraisonCommande.getNumeroLivraison())
                .statut(livraisonCommande.getStatut())
                .adresseLivraison(livraisonCommande.getAdresse())
                .build();

        // Informer le service de commande de l'état de la livraison
        if (StatutLivraison.COMPLETE.equals(livraisonReponse.statut())) {
            commandeService.informerQueLaCommandeAeteLivre(livraisonReponse);
        }

        return GenericResponse.success(livraisonReponse);
    }

    @Override
    public LivraisonCommande retrouverUneLivraison(String numeroLivraison) {
        return repository.findById(numeroLivraison)
                .orElseThrow(() -> HttpClientErrorException.create("La livraison avec le numéro #" + numeroLivraison + " n'existe pas", HttpStatus.NOT_FOUND, "Livraison non trouvé", null, null, null));
    }
}
