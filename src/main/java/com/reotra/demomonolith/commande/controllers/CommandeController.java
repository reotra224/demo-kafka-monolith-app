package com.reotra.demomonolith.commande.controllers;

import com.reotra.demomonolith.commande.dto.CreerCommandeRequest;
import com.reotra.demomonolith.commande.dto.CreerCommandeResponse;
import com.reotra.demomonolith.commande.services.CommandeService;
import com.reotra.demomonolith.common.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/commande")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping(value = "/creer")
    public ResponseEntity<GenericResponse<CreerCommandeResponse>> creerUneCommande(@RequestBody CreerCommandeRequest commandeRequest) {
        return ResponseEntity.ok(commandeService.creerUneCommandeEnValidation(commandeRequest));
    }

}
