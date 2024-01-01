package com.reotra.demomonolith.livraison.domain;

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

@Entity(name = "suivi_livraison")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SuiviLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private StatutLivraison ancienStatut;

    private StatutLivraison nouveauStatut;

    private LocalDateTime dateChangement;

    @ManyToOne
    @JoinColumn(name = "numero_livraison", nullable = false)
    private LivraisonCommande livraison;
}
