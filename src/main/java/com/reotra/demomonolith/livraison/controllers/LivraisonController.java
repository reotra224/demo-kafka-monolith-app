package com.reotra.demomonolith.livraison.controllers;

import com.reotra.demomonolith.common.dto.GenericResponse;
import com.reotra.demomonolith.livraison.dto.ChangerEtatRequest;
import com.reotra.demomonolith.livraison.dto.LivraisonCommandeReponse;
import com.reotra.demomonolith.livraison.services.LivraisonCommandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/livraison")
@AllArgsConstructor
public class LivraisonController {

    private final LivraisonCommandeService livraisonCommandeService;

    @PutMapping(value = "/changer-etat")
    public ResponseEntity<GenericResponse<LivraisonCommandeReponse>> changerEtatDuneLivraison(@RequestBody ChangerEtatRequest changerEtatRequest) {
        return ResponseEntity.ok(livraisonCommandeService.mettreAJourLetatDeLaLivraison(changerEtatRequest));
    }
}
