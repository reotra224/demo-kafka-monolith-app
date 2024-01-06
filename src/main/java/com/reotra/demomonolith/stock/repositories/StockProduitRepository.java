package com.reotra.demomonolith.stock.repositories;

import com.reotra.demomonolith.stock.domain.StockProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockProduitRepository extends JpaRepository<StockProduit, UUID> {
    Optional<StockProduit> findByProduitId(UUID uuid);
}
