package com.reotra.demomonolith.common.services.impl;

import com.reotra.demomonolith.common.domain.UCounter;
import com.reotra.demomonolith.common.repositories.UCounterRepository;
import com.reotra.demomonolith.common.services.UCounterService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UCounterServiceImpl implements UCounterService {

    private final UCounterRepository repository;

    public UCounterServiceImpl(UCounterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer genererProchaineValeur(String type) {
        UCounter counter = repository.findById(type)
                .orElseThrow(() -> HttpClientErrorException.create("Le type de counter #" + type + " n'existe pas", HttpStatus.NOT_FOUND, "Counter non trouvé", null, null, null));
        counter.setCurrentValue(counter.getCurrentValue()+1);
        counter = repository.save(counter);
        return counter.getCurrentValue();
    }
}
