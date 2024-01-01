package com.reotra.demomonolith.stock.domain;


import com.reotra.demomonolith.commande.domain.Produit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "stocks_produits")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private Integer stockActuel;

    private LocalDateTime dateCreation;

    @OneToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;
}
