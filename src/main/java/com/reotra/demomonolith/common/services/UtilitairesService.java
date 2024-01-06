package com.reotra.demomonolith.common.services;

import com.reotra.demomonolith.common.domain.CounterType;
import com.reotra.demomonolith.common.services.UCounterService;
import org.springframework.stereotype.Service;

@Service
public class UtilitairesService {

    private final UCounterService counterService;

    public UtilitairesService(UCounterService counterService) {
        this.counterService = counterService;
    }

    public String genererUnNouveauNumero(CounterType counterType) {
        return counterType.name() + counterService.genererProchaineValeur(counterType.name());
    }
}
