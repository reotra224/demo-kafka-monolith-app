package com.reotra.demomonolith.common.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "counter")
@Getter
@Setter
@ToString()
@NoArgsConstructor
@AllArgsConstructor
public class UCounter {

    @Id
    private String type;
    private Integer currentValue;
    private LocalDateTime dateChangement;
}
