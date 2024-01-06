package com.reotra.demomonolith.common.repositories;

import com.reotra.demomonolith.common.domain.UCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UCounterRepository extends JpaRepository<UCounter, String> {
}
