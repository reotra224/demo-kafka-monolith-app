package com.reotra.demomonolith.commande.services;

import com.reotra.demomonolith.commande.domain.Commande;
import com.reotra.demomonolith.commande.dto.CreerCommandeRequest;
import com.reotra.demomonolith.commande.dto.CreerCommandeResponse;
import com.reotra.demomonolith.commande.dto.TrouverCommandeResponse;
import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;

import java.util.List;

public interface CommandeService {

    GenericResponse<CreerCommandeResponse> creerUneCommandeEnValidation(CreerCommandeRequest creerCommandeDTO);
    GenericResponse<CreerCommandeResponse> validerUneCommande(String commandeID);
    List<TrouverCommandeResponse> recupererListeDesCommandesPourUnProduit(String produitID);

    Commande rechercherUneCommande(String numeroCommande);

    void informerQueLaCommandeAeteLivre(LivraisonCommandeReponse livraisonEtat);
}
