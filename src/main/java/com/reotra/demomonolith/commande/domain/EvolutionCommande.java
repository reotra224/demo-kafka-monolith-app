package com.reotra.demomonolith.commande.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "evolutions_commande")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvolutionCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private StatutCommande ancienStatut;

    private StatutCommande nouveauStatut;

    private LocalDateTime dateChangement;

    @ManyToOne
    @JoinColumn(name = "num_commande", nullable = false)
    private Commande commande;
}
