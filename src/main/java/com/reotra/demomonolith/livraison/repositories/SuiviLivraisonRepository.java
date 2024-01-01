package com.reotra.demomonolith.livraison.repositories;

import com.reotra.demomonolith.livraison.domain.SuiviLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SuiviLivraisonRepository extends JpaRepository<SuiviLivraison, UUID> {
}
