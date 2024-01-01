package com.reotra.demomonolith.commande.repositories;

import com.reotra.demomonolith.commande.domain.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, String> {
}
