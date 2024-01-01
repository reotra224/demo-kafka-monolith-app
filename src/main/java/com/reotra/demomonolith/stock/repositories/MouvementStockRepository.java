package com.reotra.demomonolith.stock.repositories;

import com.reotra.demomonolith.stock.domain.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, UUID> {
}
