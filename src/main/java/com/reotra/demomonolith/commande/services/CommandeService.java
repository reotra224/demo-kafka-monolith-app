package com.reotra.demomonolith.commande.services;

import com.reotra.demomonolith.commande.dto.CreerCommandeRequest;
import com.reotra.demomonolith.commande.dto.CreerCommandeResponse;
import com.reotra.demomonolith.commande.dto.TrouverCommandeResponse;
import com.reotra.demomonolith.common.GenericResponse;

import java.util.List;

public interface CommandeService {

    GenericResponse<CreerCommandeResponse> creerUneCommandeEnValidation(CreerCommandeRequest creerCommandeDTO);
    CreerCommandeResponse validerUneCommande(String commandeID);
    List<TrouverCommandeResponse> recupererListeDesCommandesPourUnProduit(String produitID);
}
