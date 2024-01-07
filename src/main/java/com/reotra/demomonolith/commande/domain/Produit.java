package com.reotra.demomonolith.commande.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "produits")
@Getter
@Setter
@ToString()
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private LocalDateTime dateCreation;
}
