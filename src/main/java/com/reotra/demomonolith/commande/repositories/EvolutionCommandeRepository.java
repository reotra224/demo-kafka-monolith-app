package com.reotra.demomonolith.commande.repositories;

import com.reotra.demomonolith.commande.domain.EvolutionCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvolutionCommandeRepository extends JpaRepository<EvolutionCommande, UUID> {
}
