package com.reotra.demomonolith.livraison.services;

import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeRequest;

public interface LivraisonCommandeService {
    GenericResponse<LivraisonCommandeReponse> informerCreationNouvelleCommande(LivraisonCommandeRequest livraisonCommandeRequest);
}
