package com.reotra.demomonolith.livraison.services.impl;

import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.common.services.UtilitairesService;
import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.livraison.domain.LivraisonCommande;
import com.reotra.demomonolith.livraison.domain.StatutLivraison;
import com.reotra.demomonolith.livraison.domain.SuiviLivraison;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeRequest;
import com.reotra.demomonolith.livraison.repositories.LivraisonCommandeRepository;
import com.reotra.demomonolith.livraison.repositories.SuiviLivraisonRepository;
import com.reotra.demomonolith.livraison.services.LivraisonCommandeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LivraisonCommandeServiceImpl implements LivraisonCommandeService {

    private final LivraisonCommandeRepository repository;
    private final SuiviLivraisonRepository suiviLivraisonRepository;
    private final UtilitairesService utilitairesService;

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

}
