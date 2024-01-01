package com.reotra.demomonolith.livraison.repositories;

import com.reotra.demomonolith.livraison.domain.LivraisonCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivraisonCommandeRepository extends JpaRepository<LivraisonCommande, UUID> {
}
